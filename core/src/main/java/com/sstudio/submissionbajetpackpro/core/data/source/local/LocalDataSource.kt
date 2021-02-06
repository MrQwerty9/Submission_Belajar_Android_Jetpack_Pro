package com.sstudio.submissionbajetpackpro.core.data.source.local

import androidx.paging.DataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.*
import com.sstudio.submissionbajetpackpro.core.data.source.local.room.MovieTvDao
import com.sstudio.submissionbajetpackpro.core.utils.ListType
import com.sstudio.submissionbajetpackpro.core.utils.Params
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(private val mMovieDao: MovieTvDao) {

    fun getMovieListPaging(params: Params.MovieParams): DataSource.Factory<Int, MovieEntity> =
        mMovieDao.getMovieListTypePaging(params.listType.toString())

    fun getMovieList(params: Params.MovieParams): Flow<List<MovieEntity>> =
        mMovieDao.getMovieListType(params.listType.toString())
    fun getAllFavoriteMovie(): DataSource.Factory<Int, MovieFavorite> = mMovieDao.getAllFavoriteMovie()
    fun getMovieById(movieId: Int): Flow<List<MovieEntity>> = mMovieDao.getMovieById(movieId)
    suspend fun deleteAllMovie() = mMovieDao.deleteAllMovie()

    suspend fun insertAllMovie(movie: List<MovieEntity>) = mMovieDao.insertAllMovie(movie)
    suspend fun insertMovieDetail(data: MovieEntity) = mMovieDao.insertMovieDetail(data)

    fun getTvListPaging(movieParams: Params.MovieParams): DataSource.Factory<Int, TvEntity> =
        mMovieDao.getTvListTypePaging(movieParams.listType.toString())
    fun getTvList(movieParams: Params.MovieParams): Flow<List<TvEntity>> =
        mMovieDao.getTvListType(movieParams.listType.toString())
    fun getAllFavoriteTv(): DataSource.Factory<Int, TvFavorite> = mMovieDao.getAllFavoriteTv()
    fun getTvById(tvShowId: Int): Flow<List<TvEntity>> = mMovieDao.getTvById(tvShowId)
    suspend fun deleteAllTv() = mMovieDao.deleteAllTv()

    suspend fun insertAllTv(tv: List<TvEntity>) = mMovieDao.insertAllTv(tv)
    suspend fun insertTvDetail(tv: TvEntity) = mMovieDao.insertTvDetail(tv)

    fun insertFavorite(favorite: FavoriteEntity) = mMovieDao.insertFavorite(favorite)
    fun deleteFavorite(id: Int) = mMovieDao.deleteFavorite(id)
    fun getFavoriteById(id: Int): Flow<List<FavoriteEntity>> = mMovieDao.getFavoriteById(id)

    suspend fun insertListTypeMovie(movies: List<MovieEntity>, listType: ListType){

        mMovieDao.insertListType(movies.map {
            ListTypeEntity(0, it.idMovie, listType.toString())
        })
        mMovieDao.insertAllMovie(movies)
    }

    suspend fun deleteListTypeMovie(listType: ListType){
        mMovieDao.deleteListType(listType.toString())
    }

    suspend fun insertListTypeTv(tv: List<TvEntity>, listType: ListType) {
        mMovieDao.insertListType(tv.map {
            ListTypeEntity(0, it.idTv, listType.toString())
        })
        mMovieDao.insertAllTv(tv)
    }

    suspend fun deleteListTypeTv(listType: ListType) {
        mMovieDao.deleteListType(listType.toString())
    }
}