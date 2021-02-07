package com.sstudio.submissionbajetpackpro.core.data

import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteMovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteTvEntity
import com.sstudio.submissionbajetpackpro.core.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RepoResult
import com.sstudio.submissionbajetpackpro.core.data.source.remote.paging.movie.MovieBoundaryCallback
import com.sstudio.submissionbajetpackpro.core.data.source.remote.paging.tv.TvBoundaryCallback
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.TvResponse
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.MovieHome
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.model.TvHome
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
            .setPageSize(20)
            .build()

        val local = LivePagedListBuilder(localDataSource.getMovieListPaging(params).map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }, config)
            .setBoundaryCallback(movieBoundaryCallback)
            .build()
        return RepoResult(local, state)
    }

    override fun getMovieDetail(needRefresh: Boolean, movieId: Int): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, MovieResponse.Result>(appExecutors) {
            override fun loadFromDB(): Flow<Movie> =
                localDataSource.getMovieById(movieId).map {
                    if (!it.isNullOrEmpty())
                        DataMapper.mapMovieEntitiesToDomain(it.first())
                    else
                        Movie()
                }

            override fun shouldFetch(data: Movie?): Boolean {
                data?.id == 0 || needRefresh
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse.Result>> =
                remoteDataSource.getMovieDetail(movieId)

            override suspend fun saveCallResult(data: MovieResponse.Result) {
                if (data.id == movieId) {
                    localDataSource.insertMovieDetail(
                        DataMapper.mapMovieResponseToEntities(data)
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
            localDataSource.getAllFavoriteMovie().map {
                DataMapper.mapMovieEntitiesToDomain(it.movie)
            },
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
            .setPageSize(20)
            .build()

        val local = LivePagedListBuilder(localDataSource.getTvListPaging(params).map {
            DataMapper.mapTvEntitiesToDomain(it)
        }, config)
            .setBoundaryCallback(tvBoundaryCallback)
            .build()
        return RepoResult(local, state)
    }

    override fun getTvShowDetail(needRefresh: Boolean, tvShowId: Int): Flow<Resource<Tv>> {
        return object : NetworkBoundResource<Tv, TvResponse.Result>(appExecutors) {
            override fun loadFromDB(): Flow<Tv> =
                localDataSource.getTvById(tvShowId).map {
                    if (!it.isNullOrEmpty())
                        DataMapper.mapTvEntitiesToDomain(it.first())
                    else
                        Tv()
                }

            override fun shouldFetch(data: Tv?): Boolean =
                data == Tv() || needRefresh

            override suspend fun createCall(): Flow<ApiResponse<TvResponse.Result>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override suspend fun saveCallResult(data: TvResponse.Result) {
                if (data.id == tvShowId) {
                    localDataSource.insertTvDetail(
                        DataMapper.mapTvResponseToEntities(data)
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
            localDataSource.getAllFavoriteTv().map {
                DataMapper.mapTvEntitiesToDomain(it.tv)
            },
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
        appExecutors.diskIO().execute { localDataSource.insertMovieFavorite(FavoriteMovieEntity(id)) }
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
}