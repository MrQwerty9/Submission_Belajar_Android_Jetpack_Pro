package com.sstudio.submissionbajetpackpro.core.data

import android.util.Log
import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
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
        return flow {
            val movieList = ArrayList<Resource<MovieHome>>()
            emit(listOf(Resource.Loading()))
            repeat(4) {
                val mListType = when(it){
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

                when (val remote = remoteDataSource.getAllMovie(Params.MovieParams(listType = mListType))) {
                    is ApiResponse.Success -> {
                        movieList.add(Resource.Success(
                            MovieHome(
                                listType = mListType,
                                listMovie = remote.data.results.map { movieResponseResult ->
                                    DataMapper.mapMovieResponseToDomain(movieResponseResult)
                                } as ArrayList<Movie>)))
                    }
                    is ApiResponse.Empty -> {
                        movieList.add(
                            Resource.Success(
                                MovieHome(
                                    listType = mListType,
                                    listMovie = arrayListOf()
                                )
                            )
                        )
                    }
                    is ApiResponse.Failed -> {
                        movieList.add(Resource.Error(remote.errorMessage))
                    }
                }
            }
            emit(movieList)
        }
    }

    override fun getMovieList(params: Params.MovieParams): RepoResult<Movie> {
        Log.d("mytag", "repo getMovie")
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

        val local = LivePagedListBuilder(localDataSource.getAllMovie().map {
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
                    DataMapper.mapMovieEntitiesToDomain(it)
                }

            override fun shouldFetch(data: Movie?): Boolean =
                data == null || needRefresh

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse.Result>> =
                remoteDataSource.getMovieDetail(movieId)

            override suspend fun saveCallResult(data: MovieResponse.Result) {
                if (data.id == movieId) {
                    localDataSource.insertMovieDetail(
                        DataMapper.mapMovieResponseToEntities(
                            listOf(
                                data
                            )
                        ).first()
                    )
                }
            }

            override suspend fun deleteOldDB() {

            }

            override suspend fun showData(data: MovieResponse.Result): Movie {
                return DataMapper.mapMovieResponseToDomain(data)

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
        return flow {
            val movieList = ArrayList<Resource<TvHome>>()
            emit(listOf(Resource.Loading()))
            repeat(4) {
                val mListType = when(it){
                    0 ->
                        ListType.POPULAR_TV_SHOW
                    1 ->
                        ListType.ON_AIR_TV_SHOW
                    2 ->
                        ListType.TOP_RATED_TV_SHOW
                    else ->
                        ListType.AIRING_TODAY
                }

                when (val remote = remoteDataSource.getAllTvShows(Params.MovieParams(listType = mListType))) {
                    is ApiResponse.Success -> {
                        movieList.add(Resource.Success(
                            TvHome(
                                listType = mListType,
                                listTv = remote.data.results.map { movieResponseResult ->
                                    DataMapper.mapTvResponseToDomain(movieResponseResult)
                                } as ArrayList<Tv>)))
                    }
                    is ApiResponse.Empty -> {
                        movieList.add(
                            Resource.Success(
                                TvHome(
                                    listType = mListType,
                                    listTv = arrayListOf()
                                )
                            )
                        )
                    }
                    is ApiResponse.Failed -> {
                        movieList.add(Resource.Error(remote.errorMessage))
                    }
                }
            }
            emit(movieList)
        }
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

        val local = LivePagedListBuilder(localDataSource.getAllTv().map {
            DataMapper.mapTvEntitiesToDomain(it)
        }, config)
            .setBoundaryCallback(tvBoundaryCallback)
            .build()
        return RepoResult(local, state)
    }

    override fun getTvShowDetail(needFetch: Boolean, tvShowId: Int): Flow<Resource<Tv>> {
        return object : NetworkBoundResource<Tv, TvResponse.Result>(appExecutors) {
            override fun loadFromDB(): Flow<Tv> =
                localDataSource.getTvById(tvShowId).map {
                    DataMapper.mapTvEntitiesToDomain(it)
                }

            override fun shouldFetch(data: Tv?): Boolean =
                data == null || needFetch

            override suspend fun createCall(): Flow<ApiResponse<TvResponse.Result>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override suspend fun saveCallResult(data: TvResponse.Result) {
                if (data.id == tvShowId) {
                    localDataSource.insertTvDetail(
                        DataMapper.mapTvResponseToEntities(data)
                    )
                }
            }

            override suspend fun deleteOldDB() {

            }

            override suspend fun showData(data: TvResponse.Result): Tv? {
                return null
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

    override fun setFavorite(id: Int) {
        appExecutors.diskIO().execute { localDataSource.insertFavorite(FavoriteEntity(id)) }
    }

    override fun getFavoriteById(id: Int): Flow<List<FavoriteEntity>> =
        localDataSource.getFavoriteById(id)

    override fun deleteFavorite(id: Int) {
        appExecutors.diskIO().execute { localDataSource.deleteFavorite(id) }
    }


}