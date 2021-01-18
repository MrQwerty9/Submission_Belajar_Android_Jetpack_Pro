package com.sstudio.submissionbajetpackpro.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.*

@Dao
interface MovieTvDao {

    @Query("SELECT * FROM MovieEntity")
    fun getAllMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT MovieEntity.*, FavoriteEntity.* FROM MovieEntity, FavoriteEntity WHERE MovieEntity.id = favoriteEntity.idMovieTv")
    fun getAllFavoriteMovie(): DataSource.Factory<Int, MovieFavorite>

    @Query("SELECT * FROM MovieEntity where id = :movieId")
    fun getMovieById(movieId: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovie(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(data: MovieEntity)

    @Query("SELECT * FROM TvEntity")
    fun getAllTv(): DataSource.Factory<Int, TvEntity>

    @Query(
        "SELECT *, * FROM TvEntity, FavoriteEntity WHERE TvEntity.id = favoriteEntity.idMovieTv"
    )
    fun getAllFavoriteTv(): DataSource.Factory<Int, TvFavorite>

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