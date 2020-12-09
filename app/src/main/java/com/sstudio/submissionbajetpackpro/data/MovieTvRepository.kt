package com.sstudio.submissionbajetpackpro.data

import androidx.lifecycle.*
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.data.source.remote.RemoteDataSource
import java.util.*
import kotlin.collections.ArrayList

class MovieTvRepository private constructor(private val remoteDataSource: RemoteDataSource) :
    MovieDataSource {

    companion object {
        @Volatile
        private var instance: MovieTvRepository? = null

        fun getInstance(remoteData: RemoteDataSource): MovieTvRepository =
            instance ?: synchronized(this) {
                instance ?: MovieTvRepository(remoteData)
            }
    }

    override fun getAllMovie(): LiveData<ArrayList<MovieEntity>> {
        val liveDataMovie = remoteDataSource.getAllMovie()
        return Transformations.map(liveDataMovie) {
            val movieList = ArrayList<MovieEntity>()
            for (response in it.results) {
                val movie = MovieEntity()
                movie.id = response.id
                movie.originalTitle = response.originalTitle
                movie.overview = response.overview
                movie.posterPath = response.posterPath
                movieList.add(movie)
            }
            return@map movieList
        }
    }

    override fun getAllTvShows(): LiveData<ArrayList<TvEntity>> {
        val liveDataTv = remoteDataSource.getAllTvShows()
        return Transformations.map(liveDataTv) {
            val tvList = ArrayList<TvEntity>()
            for (response in it.results) {
                val movie = TvEntity()
                movie.id = response.id
                movie.originalName = response.originalName
                movie.overview = response.overview
                movie.posterPath = response.posterPath
                tvList.add(movie)
            }
            return@map tvList
        }
    }

    override fun getMovieDetail(movieId: Int): LiveData<MovieEntity> {
        val liveDataDetail = remoteDataSource.getMovieDetail(movieId)
        return Transformations.map(liveDataDetail) { detail ->
            return@map MovieEntity(
                detail.backdropPath,
                detail.genreIds,
                detail.id,
                detail.originalTitle,
                detail.overview,
                detail.posterPath,
                detail.releaseDate,
                detail.voteAverage
            )
        }
    }

    override fun getTvShowDetail(tvShowId: Int): LiveData<TvEntity> {
        val liveDataDetail = remoteDataSource.getTvShowDetail(tvShowId)
        return Transformations.map(liveDataDetail) { detail ->
            return@map TvEntity(
                detail.backdropPath,
                detail.firstAirDate,
                detail.genreIds,
                detail.id,
                detail.originalName,
                detail.overview,
                detail.posterPath,
                detail.voteAverage
            )
        }
    }
}