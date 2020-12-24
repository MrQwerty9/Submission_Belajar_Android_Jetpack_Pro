package com.sstudio.submissionbajetpackpro.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sstudio.submissionbajetpackpro.data.source.local.entity.*

@Dao
interface MovieTvDao {

    @Query("SELECT * FROM MovieEntity")
    fun getAllMovie(): LiveData<List<MovieEntity>>

    @Transaction
    @Query("SELECT * FROM MovieEntity")
    fun getAllFavoriteMovie(): LiveData<List<MovieFavorite>>

    @Query("SELECT * FROM MovieEntity where id = :movieId")
    fun getMovieById(movieId: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovie(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(data: MovieEntity)

    @Query("SELECT * FROM TvEntity")
    fun getAllTv(): LiveData<List<TvEntity>>

    @Transaction
    @Query("SELECT * FROM TvEntity")
    fun getAllFavoriteTv(): LiveData<List<TvFavorite>>

    @Query("SELECT * FROM TvEntity where id = :tvId")
    fun getTvById(tvId: Int): LiveData<TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTv(tv: List<TvEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvDetail(tv: TvEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity where idMovieTv = :id")
    fun deleteFavorite(id: Int)

    @Query("SELECT * FROM FavoriteEntity where idMovieTv = :id")
    fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>>
}