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

    suspend fun insertAllMovie(movie: List<MovieEntity>) = mMovieDao.insertMovies(movie)
    suspend fun insertMovieDetail(data: MovieEntity) = mMovieDao.insertMovieDetail(data)

    fun getTvListPaging(movieParams: Params.MovieParams): DataSource.Factory<Int, TvEntity> =
        mMovieDao.getTvListTypePaging(movieParams.listType.toString())
    fun getTvList(movieParams: Params.MovieParams): Flow<List<TvEntity>> =
        mMovieDao.getTvListType(movieParams.listType.toString())
    fun getAllFavoriteTv(): DataSource.Factory<Int, TvFavorite> = mMovieDao.getAllFavoriteTv()
    fun getTvById(tvShowId: Int): Flow<List<TvEntity>> = mMovieDao.getTvById(tvShowId)

    suspend fun insertAllTv(tv: List<TvEntity>) = mMovieDao.insertTvShows(tv)
    suspend fun insertTvDetail(tv: TvEntity) = mMovieDao.insertTvDetail(tv)

    fun insertMovieFavorite(favorite: FavoriteMovieEntity) = mMovieDao.insertFavoriteMovie(favorite)
    fun insertTvFavorite(favorite: FavoriteTvEntity) = mMovieDao.insertFavoriteTv(favorite)
    fun deleteFavoriteMovie(id: Int) = mMovieDao.deleteFavoriteMovie(id)
    fun deleteFavoriteTv(id: Int) = mMovieDao.deleteFavoriteTv(id)
    fun getFavoriteMovieById(id: Int): Flow<List<FavoriteMovieEntity>> = mMovieDao.getFavoriteMovieById(id)
    fun getFavoriteTvById(id: Int): Flow<List<FavoriteTvEntity>> = mMovieDao.getFavoriteTvById(id)

    suspend fun insertMovieListType(movies: List<MovieEntity>, listType: ListType){
        mMovieDao.insertMovieListType(movies.map {
            ListTypeMovieEntity(0, it.idMovie, listType.toString())
        })
        mMovieDao.insertMovies(movies)
    }

    suspend fun deleteMovieList(listType: ListType){
        mMovieDao.deleteMovieList(listType.toString())
    }

    suspend fun insertTvListType(tv: List<TvEntity>, listType: ListType) {
        mMovieDao.insertTvListType(tv.map {
            ListTypeTvEntity(0, it.idTv, listType.toString())
        })
        mMovieDao.insertTvShows(tv)
    }

    suspend fun deleteTvList(listType: ListType) {
        mMovieDao.deleteTvList(listType.toString())
    }
}