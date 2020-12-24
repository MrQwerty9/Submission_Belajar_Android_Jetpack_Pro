package com.sstudio.submissionbajetpackpro.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sstudio.submissionbajetpackpro.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.data.source.local.entity.*
import com.sstudio.submissionbajetpackpro.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.data.source.remote.response.TvResponse
import com.sstudio.submissionbajetpackpro.utils.AppExecutors
import com.sstudio.submissionbajetpackpro.vo.Resource

class MovieTvRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MovieDataSource {

    companion object {
        @Volatile
        private var instance: MovieTvRepository? = null

        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource, appExecutors: AppExecutors): MovieTvRepository =
            instance ?: synchronized(this) {
                instance ?: MovieTvRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllMovie(needFetch: Boolean): LiveData<Resource<List<MovieEntity>>> {
        return object :
            NetworkBoundResource<List<MovieEntity>, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> =
                localDataSource.getAllMovie()

            override fun shouldFetch(data: List<MovieEntity>?): Boolean =
                needFetch

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteDataSource.getAllMovie()

            override fun saveCallResult(data: MovieResponse) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data.results) {
                    val movie = MovieEntity(
                        response.backdropPath,
                        response.genreIds?.joinToString(separator = ",") ?: "",
                        response.id,
                        response.originalTitle,
                        response.overview,
                        response.posterPath,
                        response.releaseDate,
                        response.voteAverage
                    )
                    movieList.add(movie)
                }
                localDataSource.insertAllMovie(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(needFetch: Boolean, movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieResponse.Result>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getMovieById(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null || needFetch

            override fun createCall(): LiveData<ApiResponse<MovieResponse.Result>> =
                remoteDataSource.getMovieDetail(movieId)

            override fun saveCallResult(data: MovieResponse.Result) {
                if (data.id == movieId) {
                    localDataSource.insertMovieDetail(MovieEntity(
                        data.backdropPath,
                        data.genreIds?.joinToString(separator = ",") ?: "",
                        data.id,
                        data.originalTitle,
                        data.overview,
                        data.posterPath,
                        data.releaseDate,
                        data.voteAverage
                    ))
                }
            }

        }.asLiveData()
    }

    override fun getAllFavoriteMovie(): LiveData<List<MovieFavorite>> {
        return Transformations.map(localDataSource.getAllFavoriteMovie()) { movie ->
            val movieFavorite = ArrayList<MovieFavorite>()
            movie.forEach {
                if (it.favoriteEntity != null) {
                    movieFavorite.add(MovieFavorite(it.movie, it.favoriteEntity))
                }
            }
            return@map movieFavorite
        }
    }

    override fun getAllTvShows(needFetch: Boolean): LiveData<Resource<List<TvEntity>>> {
        return object :
            NetworkBoundResource<List<TvEntity>, TvResponse>(appExecutors){
            override fun loadFromDB(): LiveData<List<TvEntity>> =
                localDataSource.getAllTv()

            override fun shouldFetch(data: List<TvEntity>?): Boolean =
                data == null || data.isEmpty() || needFetch

            override fun createCall(): LiveData<ApiResponse<TvResponse>> =
                remoteDataSource.getAllTvShows()

            override fun saveCallResult(data: TvResponse) {
                val tvList = ArrayList<TvEntity>()
                for (response in data.results) {
                    val tvEntity = TvEntity(
                        response.backdropPath,
                        response.firstAirDate,
                        response.genreIds?.joinToString(separator = ",") ?: "",
                        response.id,
                        response.originalName,
                        response.overview,
                        response.posterPath,
                        response.voteAverage
                    )
                    tvList.add(tvEntity)
                }
                localDataSource.insertAllTv(tvList)
            }

        }.asLiveData()
    }

    override fun getTvShowDetail(needFetch: Boolean, tvShowId: Int): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, TvResponse.Result>(appExecutors) {
            override fun loadFromDB(): LiveData<TvEntity> =
                localDataSource.getTvById(tvShowId)

            override fun shouldFetch(data: TvEntity?): Boolean =
                data == null || needFetch

            override fun createCall(): LiveData<ApiResponse<TvResponse.Result>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override fun saveCallResult(data: TvResponse.Result) {
                if (data.id == tvShowId) {
                    localDataSource.insertTvDetail(TvEntity(
                        data.backdropPath,
                        data.firstAirDate,
                        data.genreIds?.joinToString(separator = ",") ?: "",
                        data.id,
                        data.originalName,
                        data.overview,
                        data.posterPath,
                        data.voteAverage))
                }
            }
        }.asLiveData()
    }

    override fun getAllFavoriteTv(): LiveData<List<TvFavorite>> {
        return Transformations.map(localDataSource.getAllFavoriteTv()) { tvShow ->
            val tvFavorite = ArrayList<TvFavorite>()
            tvShow.forEach {
                if (it.favoriteEntity != null) {
                    tvFavorite.add(TvFavorite(it.tv, it.favoriteEntity))
                }
            }
            return@map tvFavorite
        }
    }

    override fun setFavorite(id: Int) {
        appExecutors.diskIO().execute{ localDataSource.insertFavorite(FavoriteEntity(id)) }
    }

    override fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>> = localDataSource.getFavoriteById(id)

    override fun deleteFavoriteTv(id: Int) {
        appExecutors.diskIO().execute{localDataSource.deleteFavoriteTv(id)}
    }
}