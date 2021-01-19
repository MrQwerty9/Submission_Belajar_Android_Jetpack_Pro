package com.sstudio.submissionbajetpackpro.core.data

import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.TvResponse
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.repository.IMovieTvRepository
import com.sstudio.submissionbajetpackpro.core.utils.AppExecutors
import com.sstudio.submissionbajetpackpro.core.utils.DataMapper
import com.sstudio.submissionbajetpackpro.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieTvRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    IMovieTvRepository {

    companion object {
        @Volatile
        private var instance: MovieTvRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieTvRepository =
            instance ?: synchronized(this) {
                instance ?: MovieTvRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllMovie(needFetch: Boolean): Flow<Resource<PagedList<Movie>>> {
        return object :
            NetworkBoundResource<PagedList<Movie>, MovieResponse>(appExecutors) {
            override fun loadFromDB(): Flow<PagedList<Movie>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(
                    localDataSource.getAllMovie().map { DataMapper.mapMovieEntitiesToDomain(it) },
                    config
                ).build().asFlow()
            }

            override fun shouldFetch(data: PagedList<Movie>?): Boolean =
                data == null || data.isEmpty() || needFetch

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse>> =
                remoteDataSource.getAllMovie()

            override suspend fun saveCallResult(data: MovieResponse) {
                localDataSource.insertAllMovie(DataMapper.mapMovieResponseToEntities(data.results))
            }
        }.asFlow()
    }

    override fun getMovieDetail(needFetch: Boolean, movieId: Int): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, MovieResponse.Result>(appExecutors) {
            override fun loadFromDB(): Flow<Movie> =
                localDataSource.getMovieById(movieId).map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }

            override fun shouldFetch(data: Movie?): Boolean =
                data == null || needFetch

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

    override fun setFavorite(id: Int) {
        appExecutors.diskIO().execute { localDataSource.insertFavorite(FavoriteEntity(id)) }
    }

    override fun getFavoriteById(id: Int): Flow<List<FavoriteEntity>> =
        localDataSource.getFavoriteById(id)

    override fun deleteFavorite(id: Int) {
        appExecutors.diskIO().execute { localDataSource.deleteFavorite(id) }
    }
}