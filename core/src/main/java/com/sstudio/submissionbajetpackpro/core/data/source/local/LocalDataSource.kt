package com.sstudio.submissionbajetpackpro.core.data.source.local

import androidx.paging.DataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.*
import com.sstudio.submissionbajetpackpro.core.data.source.local.room.MovieTvDao
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.utils.DataMapper
import com.sstudio.submissionbajetpackpro.core.utils.ListType
import com.sstudio.submissionbajetpackpro.core.utils.Params
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(private val mMovieDao: MovieTvDao) {

    fun getMovieListPaging(params: Params.MovieParams): DataSource.Factory<Int, Movie> =
        mMovieDao.getMovieListTypePaging(params.listType.toString()).map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }

    fun getMovieList(params: Params.MovieParams): Flow<List<MovieEntity>> =
        mMovieDao.getMovieListType(params.listType.toString())
    fun getAllFavoriteMovie(): DataSource.Factory<Int, Movie> = mMovieDao.getAllFavoriteMovie().map {
        DataMapper.mapMovieEntitiesToDomain(it.movie)
    }
    fun getMovieDetail(movieId: Int): Flow<MovieDetailEmbed> = mMovieDao.getMovieDetail(movieId)

    suspend fun insertAllMovie(movie: List<MovieEntity>) = mMovieDao.insertMovies(movie)
    suspend fun insertMovieDetail(data: MovieDetailEntity) = mMovieDao.insertMovieDetail(data)

    fun getTvListPaging(movieParams: Params.MovieParams): DataSource.Factory<Int, Tv> =
        mMovieDao.getTvListTypePaging(movieParams.listType.toString()).map {
            DataMapper.mapTvEntitiesToDomain(it)
        }
    fun getTvList(movieParams: Params.MovieParams): Flow<List<TvEntity>> =
        mMovieDao.getTvListType(movieParams.listType.toString())
    fun getAllFavoriteTv(): DataSource.Factory<Int, Tv> =
        mMovieDao.getAllFavoriteTv().map {
            DataMapper.mapTvEntitiesToDomain(it.tv)
        }
    fun getTvDetail(tvShowId: Int): Flow<TvDetailEmbed> = mMovieDao.getTvDetail(tvShowId)

    suspend fun insertAllTv(tv: List<TvEntity>) = mMovieDao.insertTvShows(tv)
    suspend fun insertTvDetail(tv: TvDetailEntity) = mMovieDao.insertTvDetail(tv)

    fun insertMovieFavorite(favorite: FavoriteMovieEntity) = mMovieDao.insertFavoriteMovie(favorite)
    fun insertTvFavorite(favorite: FavoriteTvEntity) = mMovieDao.insertFavoriteTv(favorite)
    fun deleteFavoriteMovie(id: Int) = mMovieDao.deleteFavoriteMovie(id)
    fun deleteFavoriteTv(id: Int) = mMovieDao.deleteFavoriteTv(id)
    fun getFavoriteMovieById(id: Int): Flow<List<FavoriteMovieEntity>> = mMovieDao.getFavoriteMovieById(id)
    fun getFavoriteTvById(id: Int): Flow<List<FavoriteTvEntity>> = mMovieDao.getFavoriteTvById(id)

    suspend fun insertMovieListType(movies: List<MovieEntity>, listType: ListType){
        mMovieDao.insertMovieListType(movies.map {
            MovieListEntity(0, it.id_movie, listType.toString())
        })
        mMovieDao.insertMovies(movies)
    }

    suspend fun deleteMovieList(listType: ListType){
        mMovieDao.deleteMovieList(listType.toString())
    }

    suspend fun insertTvListType(tv: List<TvEntity>, listType: ListType) {
        mMovieDao.insertTvListType(tv.map {
            TvListEntity(0, it.id_tv, listType.toString())
        })
        mMovieDao.insertTvShows(tv)
    }

    suspend fun deleteTvList(listType: ListType) {
        mMovieDao.deleteTvList(listType.toString())
    }
}