package com.sstudio.submissionbajetpackpro.data.source.local

import androidx.lifecycle.LiveData
import com.sstudio.submissionbajetpackpro.data.source.local.entity.*
import com.sstudio.submissionbajetpackpro.data.source.local.room.MovieTvDao

class LocalDataSource private constructor(private val mMovieDao: MovieTvDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieTvDao: MovieTvDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieTvDao)
    }

    fun getAllMovie(): LiveData<List<MovieEntity>> = mMovieDao.getAllMovie()
    fun getAllFavoriteMovie(): LiveData<List<MovieFavorite>> = mMovieDao.getAllFavoriteMovie()
    fun getMovieById(movieId: Int): LiveData<MovieEntity> = mMovieDao.getMovieById(movieId)

    fun insertAllMovie(movie: List<MovieEntity>) = mMovieDao.insertAllMovie(movie)
    fun insertMovieDetail(data: MovieEntity) = mMovieDao.insertMovieDetail(data)

    fun getAllTv(): LiveData<List<TvEntity>> = mMovieDao.getAllTv()
    fun getAllFavoriteTv(): LiveData<List<TvFavorite>> = mMovieDao.getAllFavoriteTv()
    fun getTvById(tvShowId: Int): LiveData<TvEntity> = mMovieDao.getTvById(tvShowId)

    fun insertAllTv(tv: List<TvEntity>) = mMovieDao.insertAllTv(tv)
    fun insertTvDetail(tv: TvEntity) = mMovieDao.insertTvDetail(tv)

    fun insertFavorite(favorite: FavoriteEntity) = mMovieDao.insertFavorite(favorite)
    fun deleteFavoriteTv(id: Int) = mMovieDao.deleteFavorite(id)
    fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>> = mMovieDao.getFavoriteById(id)
}