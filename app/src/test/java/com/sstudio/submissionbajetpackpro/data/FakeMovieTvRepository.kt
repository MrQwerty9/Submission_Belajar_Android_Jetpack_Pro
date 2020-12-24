package com.sstudio.submissionbajetpackpro.data

import androidx.lifecycle.LiveData
import com.sstudio.submissionbajetpackpro.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieFavorite
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvFavorite
import com.sstudio.submissionbajetpackpro.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.data.source.remote.response.TvResponse
import com.sstudio.submissionbajetpackpro.utils.AppExecutors
import com.sstudio.submissionbajetpackpro.vo.Resource

class FakeMovieTvRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MovieDataSource {

    override fun getAllMovie(): LiveData<Resource<List<MovieEntity>>> {
        return object :
            NetworkBoundResource<List<MovieEntity>, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> =
                localDataSource.getAllMovie()

            override fun shouldFetch(data: List<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteDataSource.getAllMovie()

            override fun saveCallResult(data: MovieResponse) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data.results) {
                    val movie = MovieEntity()
                    movie.id = response.id
                    movie.originalTitle = response.originalTitle
                    movie.overview = response.overview
                    movie.posterPath = response.posterPath
                    movieList.add(movie)
                }
                localDataSource.insertAllMovie(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieResponse.Result>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getMovieById(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<MovieResponse.Result>> =
                remoteDataSource.getMovieDetail(movieId)

            override fun saveCallResult(data: MovieResponse.Result) {
                if (data.id == movieId) {
                    localDataSource.insertMovieDetail(
                        MovieEntity(
                            data.backdropPath,
                            data.genreIds?.joinToString(separator = ",") ?: "",
                            data.id,
                            data.originalTitle,
                            data.overview,
                            data.posterPath,
                            data.releaseDate,
                            data.voteAverage
                        )
                    )
                }
            }

        }.asLiveData()
    }

    override fun getAllFavoriteMovie(): LiveData<List<MovieFavorite>> =
        localDataSource.getAllFavoriteMovie()

    override fun setFavoriteMovie(movieEntity: MovieEntity) {
        appExecutors.diskIO().execute { localDataSource.insertFavorite(movieEntity) }
    }

    override fun getAllTvShows(): LiveData<Resource<List<TvEntity>>> {
        return object :
            NetworkBoundResource<List<TvEntity>, TvResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<TvEntity>> =
                localDataSource.getAllTv()

            override fun shouldFetch(data: List<TvEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<TvResponse>> =
                remoteDataSource.getAllTvShows()

            override fun saveCallResult(data: TvResponse) {
                val tvList = ArrayList<TvEntity>()
                for (response in data.results) {
                    val movie = TvEntity()
                    movie.id = response.id
                    movie.originalName = response.originalName
                    movie.overview = response.overview
                    movie.posterPath = response.posterPath
                    tvList.add(movie)
                }
                localDataSource.insertAllTv(tvList)
            }

        }.asLiveData()
    }

    override fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, TvResponse.Result>(appExecutors) {
            override fun loadFromDB(): LiveData<TvEntity> =
                localDataSource.getTvById(tvShowId)

            override fun shouldFetch(data: TvEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<TvResponse.Result>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override fun saveCallResult(data: TvResponse.Result) {
                if (data.id == tvShowId) {
                    localDataSource.insertTvDetail(
                        TvEntity(
                            data.backdropPath,
                            data.firstAirDate,
                            data.genreIds?.joinToString(separator = ",") ?: "",
                            data.id,
                            data.originalName,
                            data.overview,
                            data.posterPath,
                            data.voteAverage
                        )
                    )
                }
            }
        }.asLiveData()
    }

    override fun getAllFavoriteTv(): LiveData<List<TvFavorite>> =
        localDataSource.getAllFavoriteTv()

    override fun setFavorite(tvEntity: TvEntity) {
        appExecutors.diskIO().execute { localDataSource.insertFavorite(tvEntity) }
    }
}