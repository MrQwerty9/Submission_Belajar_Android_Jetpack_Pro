package com.sstudio.submissionbajetpackpro.core.data.source.local

import androidx.paging.DataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.*
import com.sstudio.submissionbajetpackpro.core.data.source.local.room.MovieTvDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(private val mMovieDao: MovieTvDao) {

    fun getAllMovie(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getAllMovie()
    fun getAllFavoriteMovie(): DataSource.Factory<Int, MovieFavorite> = mMovieDao.getAllFavoriteMovie()
    fun getMovieById(movieId: Int): Flow<MovieEntity> = mMovieDao.getMovieById(movieId)
    suspend fun deleteAllMovie() = mMovieDao.deleteAllMovie()

    suspend fun insertAllMovie(movie: List<MovieEntity>) = mMovieDao.insertAllMovie(movie)
    suspend fun insertMovieDetail(data: MovieEntity) = mMovieDao.insertMovieDetail(data)

    fun getAllTv(): DataSource.Factory<Int, TvEntity> = mMovieDao.getAllTv()
    fun getAllFavoriteTv(): DataSource.Factory<Int, TvFavorite> = mMovieDao.getAllFavoriteTv()
    fun getTvById(tvShowId: Int): Flow<TvEntity> = mMovieDao.getTvById(tvShowId)
    suspend fun deleteAllTv() = mMovieDao.deleteAllTv()

    suspend fun insertAllTv(tv: List<TvEntity>) = mMovieDao.insertAllTv(tv)
    suspend fun insertTvDetail(tv: TvEntity) = mMovieDao.insertTvDetail(tv)

    fun insertFavorite(favorite: FavoriteEntity) = mMovieDao.insertFavorite(favorite)
    fun deleteFavorite(id: Int) = mMovieDao.deleteFavorite(id)
    fun getFavoriteById(id: Int): Flow<List<FavoriteEntity>> = mMovieDao.getFavoriteById(id)
}