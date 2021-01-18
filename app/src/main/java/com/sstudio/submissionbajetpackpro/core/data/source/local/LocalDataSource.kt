package com.sstudio.submissionbajetpackpro.core.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.*
import com.sstudio.submissionbajetpackpro.core.data.source.local.room.MovieTvDao

class LocalDataSource private constructor(private val mMovieDao: MovieTvDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieTvDao: MovieTvDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieTvDao)
    }

    fun getAllMovie(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getAllMovie()
    fun getAllFavoriteMovie(): DataSource.Factory<Int, MovieFavorite> = mMovieDao.getAllFavoriteMovie()
    fun getMovieById(movieId: Int): LiveData<MovieEntity> = mMovieDao.getMovieById(movieId)

    fun insertAllMovie(movie: List<MovieEntity>) = mMovieDao.insertAllMovie(movie)
    fun insertMovieDetail(data: MovieEntity) = mMovieDao.insertMovieDetail(data)

    fun getAllTv(): DataSource.Factory<Int, TvEntity> = mMovieDao.getAllTv()
    fun getAllFavoriteTv(): DataSource.Factory<Int, TvFavorite> = mMovieDao.getAllFavoriteTv()
    fun getTvById(tvShowId: Int): LiveData<TvEntity> = mMovieDao.getTvById(tvShowId)

    fun insertAllTv(tv: List<TvEntity>) = mMovieDao.insertAllTv(tv)
    fun insertTvDetail(tv: TvEntity) = mMovieDao.insertTvDetail(tv)

    fun insertFavorite(favorite: FavoriteEntity) = mMovieDao.insertFavorite(favorite)
    fun deleteFavorite(id: Int) = mMovieDao.deleteFavorite(id)
    fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>> = mMovieDao.getFavoriteById(id)
}