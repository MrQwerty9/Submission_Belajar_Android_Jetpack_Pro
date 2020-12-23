package com.sstudio.submissionbajetpackpro.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sstudio.submissionbajetpackpro.data.source.local.entity.*

@Dao
interface MovieTvDao {

    @Query("SELECT * FROM movie_entities")
    fun getAllMovie(): LiveData<List<MovieEntity>>

    @Transaction
    @Query("SELECT * FROM movie_entities")
    fun getAllFavoriteMovie(): LiveData<List<MovieFavorite>>

    @Query("SELECT * FROM movie_entities where movieId = :movieId")
    fun getMovieById(movieId: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovie(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(data: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: MovieEntity)

    @Delete
    fun deleteFavoriteMovie(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM tv_entities")
    fun getAllTv(): LiveData<List<TvEntity>>

    @Transaction
    @Query("SELECT * FROM tv_entities")
    fun getAllFavoriteTv(): LiveData<List<TvFavorite>>

    @Query("SELECT * FROM tv_entities where tvId = :tvId")
    fun getTvById(tvId: Int): LiveData<TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTv(tv: List<TvEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvDetail(tv: TvEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteTv(tv: TvEntity)

    @Delete
    fun deleteFavoriteTv(favoriteEntity: FavoriteEntity)
}