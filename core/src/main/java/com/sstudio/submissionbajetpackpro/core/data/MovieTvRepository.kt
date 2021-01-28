package com.sstudio.submissionbajetpackpro.core.data

import android.util.Log
import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.paging.movie.MovieDataSourceFactory
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.TvResponse
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.repository.IMovieTvRepository
import com.sstudio.submissionbajetpackpro.core.utils.AppExecutors
import com.sstudio.submissionbajetpackpro.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*

class MovieTvRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    IMovieTvRepository {

    fun getAllMovie(
        needRefresh: Boolean,
        movieDataSourceFactory: MovieDataSourceFactory
    ): Flow<ApiResponse<PagedList<Movie>>> {
        return flow {
//            val state = Transformations.switchMap(
//                movieDataSourceFactory.mutableLiveData,
//                MovieDataSource::state
//            )
//            movieDataSourceFactory.movieDataSource?.setState()
//            coroutineScope {
//                val state = async {
//                    movieDataSourceFactory.movieDataSource?.getState()?.first()
//                }.await()
//                when (state) {
//                    NetworkState.LOADING -> {
//                        Log.d("mytag", "repo loading")
//                    }
//                    NetworkState.SUCCESS -> {
//                        Log.d("mytag", "repo success")
//                    }
//                    else ->
//                        Log.d("mytag", "repo else")
//                }
//            }

            val config = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build()

            val bbb: Flow<PagedList<Movie>> = (LivePagedListBuilder(movieDataSourceFactory.map {
                when (it) {
                    is ApiResponse.Success -> {
                        DataMapper.mapMovieResponseToDomain(it.data.results.first())
                    }
                    is ApiResponse.Empty -> {
                        Movie()
                    }
                    is ApiResponse.Failed -> {
                        it.errorMessage as Movie
                    }
                }
            }, config)
                .setFetchExecutor(appExecutors.networkIO())
                .build()).asFlow()

            val dataSource = movieDataSourceFactory.map {
                when (it) {
                    is ApiResponse.Success -> {
                        ApiResponse.Success(bbb)
                    }
                    is ApiResponse.Empty -> {
                        ApiResponse.Empty
                    }
                    is ApiResponse.Failed -> {
                        ApiResponse.Failed(it.errorMessage)
                    }
                }
            }

            val aaa: ApiResponse<Flow<PagedList<Movie>>>? = LivePagedListBuilder(dataSource, config)
                .setFetchExecutor(appExecutors.networkIO())
                .build().asFlow().first().first()
//            emitAll(aaa.asFlow())

            coroutineScope {
                val abc: Flow<ApiResponse<PagedList<Movie>>> = async {
                    Log.d("mytag", "repo async")
                    flow {
                            when (aaa) {
                                is ApiResponse.Success -> {
                                    Log.d("mytag", "repo suc")
                                    emit(ApiResponse.Success(aaa.data.first()))
                                }
//                                NetworkState.Status.SUCCESS -> {
//                                    Log.d("mytag", "repo suc")
//                                    Resource.Success(it)
//                                }
//                            NetworkState.Status.LOADING ->{
//                                Log.d("mytag", "repo loading")
//                                Resource.Success(it)
//                            }
//                            NetworkState.Status.FAILED ->{
//                                Log.d("mytag", "repo fa")
//                                Resource.Success(it)
//                            }
//                            else ->{
//                                Log.d("mytag", "repo els")
//                                Resource.Success(it)
//                            }
                            }
                    }
                }.await()
                emitAll(abc)
            }
        }
    }

    override fun getAllMovie(needRefresh: Boolean): Flow<PagedList<Movie>> {
        return flow {
            val movieDataSourceFactory = MovieDataSourceFactory(remoteDataSource)
            val config = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build()

            emitAll(
                LivePagedListBuilder(movieDataSourceFactory.map {
                    DataMapper.mapMovieResponseToDomain(
                        when (it) {
                            is ApiResponse.Success -> {
                                Log.d("mytag", "repo suc")
                                it.data.results.first()
                            }
                            is ApiResponse.Empty -> {
                                Log.d("mytag", "repo suc")
                                MovieResponse.Result()
                            }
                            is ApiResponse.Failed -> {
                                Log.d("mytag", "repo suc")
                                (it.errorMessage) as MovieResponse.Result
                            }
                        }
                    )
                }, config)
                    .setFetchExecutor(appExecutors.networkIO())
                    .build().asFlow().flowOn(Dispatchers.IO)
            )
        }

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

    override fun getAllTvShows(needFetch: Boolean): Flow<Resource<PagedList<Tv>>> {
        return object :
            NetworkBoundResource<PagedList<Tv>, TvResponse>(appExecutors) {
            override fun loadFromDB(): Flow<PagedList<Tv>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(
                    localDataSource.getAllTv().map {
                        DataMapper.mapTvEntitiesToDomain(it)
                    },
                    config
                ).build().asFlow()
            }

            override fun shouldFetch(data: PagedList<Tv>?): Boolean =
                data == null || data.isEmpty() || needFetch

            override suspend fun createCall(): Flow<ApiResponse<TvResponse>> =
                remoteDataSource.getAllTvShows()

            override suspend fun saveCallResult(data: TvResponse) {
                localDataSource.insertAllTv(
                    data.results.map { DataMapper.mapTvResponseToEntities(it) }
                )
            }

            override suspend fun deleteOldDB() {

            }

            override suspend fun showData(data: TvResponse): PagedList<Tv>? {
                return null
            }


        }.asFlow()
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