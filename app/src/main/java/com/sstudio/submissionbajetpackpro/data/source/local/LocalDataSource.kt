package com.sstudio.submissionbajetpackpro.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.sstudio.submissionbajetpackpro.data.source.local.entity.*
import com.sstudio.submissionbajetpackpro.data.source.local.room.MovieTvDao

class LocalDataSource(private val mMovieDao: MovieTvDao) {

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
    fun deleteFavoriteTv(id: Int) = mMovieDao.deleteFavorite(id)
    fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>> = mMovieDao.getFavoriteById(id)
}