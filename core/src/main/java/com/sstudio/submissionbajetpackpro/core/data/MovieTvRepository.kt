package com.sstudio.submissionbajetpackpro.core.data

import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteMovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteTvEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.MovieGenresEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.TvGenresEntity
import com.sstudio.submissionbajetpackpro.core.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RepoResult
import com.sstudio.submissionbajetpackpro.core.data.source.remote.paging.movie.MovieBoundaryCallback
import com.sstudio.submissionbajetpackpro.core.data.source.remote.paging.tv.TvBoundaryCallback
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.GenresResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieDetailResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.TvDetailResponse
import com.sstudio.submissionbajetpackpro.core.domain.model.*
import com.sstudio.submissionbajetpackpro.core.domain.repository.IMovieTvRepository
import com.sstudio.submissionbajetpackpro.core.utils.AppExecutors
import com.sstudio.submissionbajetpackpro.core.utils.DataMapper
import com.sstudio.submissionbajetpackpro.core.utils.ListType
import com.sstudio.submissionbajetpackpro.core.utils.Params
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class MovieTvRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    IMovieTvRepository {

    override fun getMovieHome(): Flow<List<Resource<MovieHome>>> {
        val typeListTotal = 4
        return flow {
            val movieList = ArrayList<Resource<MovieHome>>()
            //local
            repeat(typeListTotal) { times ->
                val mListType = listTypeMovieHome(times)
                val local =
                    localDataSource.getMovieList(Params.MovieParams(listType = mListType)).map {
                        if (it.size >= 9)
                            it.subList(0, 9)
                        else
                            it
                    }
                movieList.add(Resource.Loading(
                    MovieHome(
                        listType = mListType,
                        listMovie = local.first().map {
                            DataMapper.mapMovieEntitiesToDomain(it)
                        } as ArrayList<Movie>)
                ))

                emit(movieList)
            }

            //network
            repeat(typeListTotal) { times ->
                val mListType = listTypeMovieHome(times)
                when (val remote =
                    remoteDataSource.getAllMovie(Params.MovieParams(listType = mListType))) {
                    is ApiResponse.Success -> {
                        movieList[times] = Resource.Success(
                            MovieHome(
                                listType = mListType,
                                listMovie = remote.data.results.map { movieResponseResult ->
                                    DataMapper.mapMovieResponseToDomain(movieResponseResult)
                                } as ArrayList<Movie>))

                        localDataSource.deleteMovieList(mListType)
                        localDataSource.insertMovieListType(remote.data.results.map { movieResponseResult ->
                            DataMapper.mapMovieResponseToEntities(movieResponseResult)
                        }, mListType)
                    }
                    is ApiResponse.Empty -> {
                        movieList[times] = Resource.Success(
                            MovieHome(
                                listType = mListType,
                                listMovie = arrayListOf()
                            )
                        )
                    }
                    is ApiResponse.Failed -> {
                        movieList[times] = Resource.Error(remote.errorMessage)
                    }
                }
                emit(movieList)
            }
        }
    }

    private fun listTypeMovieHome(times: Int) =
        when (times) {
            0 ->
                ListType.POPULAR
            1 ->
                ListType.UPCOMING
            2 ->
                ListType.TOP_RATED
            3 ->
                ListType.NOW_PLAYING
            else ->
                ListType.EMPTY
        }

    override fun getMovieList(params: Params.MovieParams): RepoResult<Movie> {
        val movieBoundaryCallback = MovieBoundaryCallback(
            remoteDataSource,
            localDataSource,
            params
        )
        val state = movieBoundaryCallback.state
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(10)
            .build()

        val local = LivePagedListBuilder(localDataSource.getMovieListPaging(params), config)
            .setBoundaryCallback(movieBoundaryCallback)
            .build()
        return RepoResult(local, state)
    }

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> {
        return object : NetworkBoundResource<MovieDetail, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): Flow<MovieDetail> =
                localDataSource.getMovieDetail(movieId).map {
                    DataMapper.mapMovieDetailEntitiesToDomain(it)
                }

            override fun shouldFetch(data: MovieDetail?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getMovieDetail(movieId)

            override suspend fun saveCallResult(data: MovieDetailResponse) {
                if (data.id == movieId) {
                    localDataSource.insertAllMovie(
                        listOf(
                            DataMapper.mapMovieDetailResponseToMovieEntity(
                                data
                            )
                        )
                    )
                    localDataSource.insertMovieDetail(
                        DataMapper.mapMovieDetailResponseToEntities(data)
                    )
                }
            }
        }.asFlow()
    }

    override fun getAllFavoriteMovie(): Flow<PagedList<Movie>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(
            localDataSource.getAllFavoriteMovie(),
            config
        ).build().asFlow()
    }

    override fun getSearchMovie(query: String): Flow<Resource<List<Movie>>> {
        val data = remoteDataSource.getSearchMovie(query)
        return flow {
            emit(Resource.Loading())
            when (val apiResponse = data.first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(
                        apiResponse.data.map {
                            DataMapper.mapMovieResponseToDomain(it)
                        }
                    ))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success<List<Movie>>(listOf()))
                }
                is ApiResponse.Failed -> {
                    emit(Resource.Error<List<Movie>>(apiResponse.errorMessage))
                }
            }
        }
    }

    override fun getTvHome(): Flow<List<Resource<TvHome>>> {
        val listTypeTotal = 4
        return flow {
            val movieList = ArrayList<Resource<TvHome>>()
            repeat(listTypeTotal) { times ->
                val mListType = listTypeTvHome(times)
                val local =
                    localDataSource.getTvList(Params.MovieParams(listType = mListType)).map {
                        if (it.size >= 9)
                            it.subList(0, 9)
                        else
                            it
                    }
                movieList.add(Resource.Loading(
                    TvHome(
                        listType = mListType,
                        listTv = local.first().map {
                            DataMapper.mapTvEntitiesToDomain(it)
                        } as ArrayList<Tv>)
                ))

                emit(movieList)
            }
            repeat(listTypeTotal) { times ->
                val mListType = listTypeTvHome(times)
                when (val remote =
                    remoteDataSource.getAllTvShows(Params.MovieParams(listType = mListType))) {
                    is ApiResponse.Success -> {
                        movieList[times] = Resource.Success(
                            TvHome(
                                listType = mListType,
                                listTv = remote.data.results.map { movieResponseResult ->
                                    DataMapper.mapTvResponseToDomain(movieResponseResult)
                                } as ArrayList<Tv>))

                        localDataSource.deleteTvList(mListType)
                        localDataSource.insertTvListType(remote.data.results.map { movieResponseResult ->
                            DataMapper.mapTvResponseToEntities(movieResponseResult)
                        }, mListType)
                    }
                    is ApiResponse.Empty -> {
                        movieList[times] = Resource.Success(
                            TvHome(
                                listType = mListType,
                                listTv = arrayListOf()
                            )
                        )
                    }
                    is ApiResponse.Failed -> {
                        movieList[times] = Resource.Error(remote.errorMessage)
                    }
                }
                emit(movieList)
            }
        }
    }

    private fun listTypeTvHome(times: Int) =
        when (times) {
            0 ->
                ListType.POPULAR_TV_SHOW
            1 ->
                ListType.ON_AIR_TV_SHOW
            2 ->
                ListType.TOP_RATED_TV_SHOW
            else ->
                ListType.AIRING_TODAY
        }

    override fun getTvShowsList(params: Params.MovieParams): RepoResult<Tv> {
        val tvBoundaryCallback = TvBoundaryCallback(
            remoteDataSource,
            localDataSource,
            params
        )
        val state = tvBoundaryCallback.state
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(10)
            .build()

        val local = LivePagedListBuilder(localDataSource.getTvListPaging(params), config)
            .setBoundaryCallback(tvBoundaryCallback)
            .build()
        return RepoResult(local, state)
    }

    override fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvDetail>> {
        return object : NetworkBoundResource<TvDetail, TvDetailResponse>(appExecutors) {
            override fun loadFromDB(): Flow<TvDetail> =
                localDataSource.getTvDetail(tvShowId).map {
                    DataMapper.mapTvDetailEntitiesToDomain(it)
                }

            override fun shouldFetch(data: TvDetail?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<TvDetailResponse>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override suspend fun saveCallResult(data: TvDetailResponse) {
                if (data.id == tvShowId) {
                    localDataSource.insertAllTv(
                        listOf(
                            DataMapper.mapTvDetailResponseToTvEntities(
                                data
                            )
                        )
                    )
                    localDataSource.insertTvDetail(
                        DataMapper.mapTvDetailResponseToEntities(data)
                    )
                }
            }
        }.asFlow()
    }

    override fun getAllFavoriteTv(): Flow<PagedList<Tv>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(
            localDataSource.getAllFavoriteTv(),
            config
        ).build().asFlow()
    }

    override fun getSearchTv(query: String): Flow<Resource<List<Tv>>> {
        val data = remoteDataSource.getSearchTv(query)
        return flow {
            emit(Resource.Loading())
            when (val apiResponse = data.first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(
                        apiResponse.data.map {
                            DataMapper.mapTvResponseToDomain(it)
                        }
                    ))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success<List<Tv>>(listOf()))
                }
                is ApiResponse.Failed -> {
                    emit(Resource.Error<List<Tv>>(apiResponse.errorMessage))
                }
            }
        }
    }

    override fun setFavoriteMovie(id: Int) {
        appExecutors.diskIO()
            .execute { localDataSource.insertMovieFavorite(FavoriteMovieEntity(id)) }
    }

    override fun getFavoriteMovieById(id: Int): Flow<List<FavoriteMovieEntity>> =
        localDataSource.getFavoriteMovieById(id)

    override fun deleteFavoriteMovie(id: Int) {
        appExecutors.diskIO().execute { localDataSource.deleteFavoriteMovie(id) }
    }

    override fun setFavoriteTv(id: Int) {
        appExecutors.diskIO().execute { localDataSource.insertTvFavorite(FavoriteTvEntity(id)) }
    }

    override fun getFavoriteTvById(id: Int): Flow<List<FavoriteTvEntity>> =
        localDataSource.getFavoriteTvById(id)

    override fun deleteFavoriteTv(id: Int) {
        appExecutors.diskIO().execute { localDataSource.deleteFavoriteTv(id) }
    }

    override fun getCreditsMovie(id: Int): Flow<Resource<Credits>> =
        flow {
            val data = remoteDataSource.getCreditsMovie(id)
            emit(Resource.Loading())
            when (val apiResponse = data.first()) {
                is ApiResponse.Success -> {
                    emit(
                        Resource.Success(
                            DataMapper.mapCreditsResponseToDomain(apiResponse.data)
                        )
                    )
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(Credits()))
                }
                is ApiResponse.Failed -> {
                    emit(Resource.Error<Credits>(apiResponse.errorMessage))
                }
            }
        }


    override fun getCreditsTv(id: Int): Flow<Resource<Credits>> =
        flow {
            val data = remoteDataSource.getCreditsTv(id)
            emit(Resource.Loading())
            when (val apiResponse = data.first()) {
                is ApiResponse.Success -> {
                    emit(
                        Resource.Success(
                            DataMapper.mapCreditsResponseToDomain(apiResponse.data)
                        )
                    )
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(Credits()))
                }
                is ApiResponse.Failed -> {
                    emit(Resource.Error<Credits>(apiResponse.errorMessage))
                }
            }
        }

    override fun getVideoMovie(id: Int): Flow<Resource<List<Video>>> =
        flow {
            val data = remoteDataSource.getVideoMovie(id)
            emit(Resource.Loading())
            when (val apiResponse = data.first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(
                        apiResponse.data.video.map {
                            DataMapper.mapVideoResponseToDomain(it)
                        }
                    ))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(listOf(Video())))
                }
                is ApiResponse.Failed -> {
                    emit(Resource.Error<List<Video>>(apiResponse.errorMessage))
                }
            }
        }

    override fun getVideoTv(id: Int): Flow<Resource<List<Video>>> =
        flow {
            val data = remoteDataSource.getVideoTv(id)
            emit(Resource.Loading())
            when (val apiResponse = data.first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(
                        apiResponse.data.video.map {
                            DataMapper.mapVideoResponseToDomain(it)
                        }
                    ))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(listOf(Video())))
                }
                is ApiResponse.Failed -> {
                    emit(Resource.Error<List<Video>>(apiResponse.errorMessage))
                }
            }
        }

    override fun getSimilarMovie(id: Int): Flow<Resource<List<Movie>>> =
        flow {
            val data = remoteDataSource.getSimilarMovie(id)
            emit(Resource.Loading())
            when (val apiResponse = data.first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(
                        apiResponse.data.results.map {
                            DataMapper.mapMovieResponseToDomain(it)
                        }
                    ))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(listOf(Movie())))
                }
                is ApiResponse.Failed -> {
                    emit(Resource.Error<List<Movie>>(apiResponse.errorMessage))
                }
            }
        }

    override fun getSimilarTv(id: Int): Flow<Resource<List<Tv>>> =
        flow {
            val data = remoteDataSource.getSimilarTv(id)
            emit(Resource.Loading())
            when (val apiResponse = data.first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(
                        apiResponse.data.results.map {
                            DataMapper.mapTvResponseToDomain(it)
                        }
                    ))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(listOf(Tv())))
                }
                is ApiResponse.Failed -> {
                    emit(Resource.Error<List<Tv>>(apiResponse.errorMessage))
                }
            }
        }

    override fun getAllGenreMovie(): Flow<Resource<List<Genre>>> {
        return object : NetworkBoundResource<List<Genre>, GenresResponse>(appExecutors) {
            override fun loadFromDB(): Flow<List<Genre>> =
                localDataSource.getAllGenreMovie().map { list ->
                    list.map {
                        Genre(id = it.id, name = it.name)
                    }
                }

            override fun shouldFetch(data: List<Genre>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<GenresResponse>> =
                remoteDataSource.getAllGenreMovie()

            override suspend fun saveCallResult(data: GenresResponse) {
                localDataSource.insertAllGenreMovie(data.genres.map {
                    MovieGenresEntity(id = it.id, name = it.name)
                })
            }
        }.asFlow()
    }

    override fun getAllGenreTv(): Flow<Resource<List<Genre>>> {
        return object : NetworkBoundResource<List<Genre>, GenresResponse>(appExecutors) {
            override fun loadFromDB(): Flow<List<Genre>> =
                localDataSource.getAllGenreTv().map { list ->
                    list.map {
                        Genre(id = it.id, name = it.name)
                    }
                }

            override fun shouldFetch(data: List<Genre>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<GenresResponse>> =
                remoteDataSource.getAllGenreTv()

            override suspend fun saveCallResult(data: GenresResponse) {
                localDataSource.insertAllGenreTv(data.genres.map {
                    TvGenresEntity(id = it.id, name = it.name)
                })
            }
        }.asFlow()
    }
}