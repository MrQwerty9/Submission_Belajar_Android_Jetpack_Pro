package com.sstudio.submissionbajetpackpro.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
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
import com.sstudio.submissionbajetpackpro.core.domain.repository.MovieDataSource
import com.sstudio.submissionbajetpackpro.core.utils.AppExecutors
import com.sstudio.submissionbajetpackpro.core.utils.DataMapper
import com.sstudio.submissionbajetpackpro.vo.Resource

class FakeMovieTvRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MovieDataSource {

    override fun getAllMovie(needFetch: Boolean): LiveData<Resource<PagedList<Movie>>> {
        return object :
            NetworkBoundResource<PagedList<Movie>, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<Movie>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                val livePageList =
                    LivePagedListBuilder(localDataSource.getAllMovie(), config).build()
                return Transformations.map(livePageList) { pagedList ->
                    pagedList.map {
                        DataMapper.mapMovieEntitiesToDomain(it)
                    } as PagedList<Movie>?
                }
            }

            override fun shouldFetch(data: PagedList<Movie>?): Boolean =
                data == null || data.isEmpty() || needFetch

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteDataSource.getAllMovie()

            override fun saveCallResult(data: MovieResponse) {
                localDataSource.insertAllMovie(DataMapper.mapMovieResponseToEntities(data.results))
            }
        }.asLiveData()
    }

    override fun getMovieDetail(needFetch: Boolean, movieId: Int): LiveData<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, MovieResponse.Result>(appExecutors) {
            override fun loadFromDB(): LiveData<Movie> =
                Transformations.map(localDataSource.getMovieById(movieId)) {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }

            override fun shouldFetch(data: Movie?): Boolean =
                data == null || needFetch

            override fun createCall(): LiveData<ApiResponse<MovieResponse.Result>> =
                remoteDataSource.getMovieDetail(movieId)

            override fun saveCallResult(data: MovieResponse.Result) {
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

        }.asLiveData()
    }

    override fun getAllFavoriteMovie(): LiveData<PagedList<Movie>> {
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
        ).build()
    }

    override fun getAllTvShows(needFetch: Boolean): LiveData<Resource<PagedList<Tv>>> {
        return object :
            NetworkBoundResource<PagedList<Tv>, TvResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<Tv>> {
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
                ).build()
            }

            override fun shouldFetch(data: PagedList<Tv>?): Boolean =
                data == null || data.isEmpty() || needFetch

            override fun createCall(): LiveData<ApiResponse<TvResponse>> =
                remoteDataSource.getAllTvShows()

            override fun saveCallResult(data: TvResponse) {
                localDataSource.insertAllTv(
                    data.results.map { DataMapper.mapTvResponseToEntities(it) }
                )
            }

        }.asLiveData()
    }

    override fun getTvShowDetail(needFetch: Boolean, tvShowId: Int): LiveData<Resource<Tv>> {
        return object : NetworkBoundResource<Tv, TvResponse.Result>(appExecutors) {
            override fun loadFromDB(): LiveData<Tv> =
                Transformations.map(localDataSource.getTvById(tvShowId)) {
                    DataMapper.mapTvEntitiesToDomain(it)
                }

            override fun shouldFetch(data: Tv?): Boolean =
                data == null || needFetch

            override fun createCall(): LiveData<ApiResponse<TvResponse.Result>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override fun saveCallResult(data: TvResponse.Result) {
                if (data.id == tvShowId) {
                    localDataSource.insertTvDetail(
                        DataMapper.mapTvResponseToEntities(data)
                    )
                }
            }
        }.asLiveData()
    }

    override fun getAllFavoriteTv(): LiveData<PagedList<Tv>> {
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
        ).build()
    }

    override fun setFavorite(id: Int) {
        appExecutors.diskIO().execute { localDataSource.insertFavorite(FavoriteEntity(id)) }
    }

    override fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>> =
        localDataSource.getFavoriteById(id)

    override fun deleteFavorite(id: Int) {
        appExecutors.diskIO().execute { localDataSource.deleteFavorite(id) }
    }
}