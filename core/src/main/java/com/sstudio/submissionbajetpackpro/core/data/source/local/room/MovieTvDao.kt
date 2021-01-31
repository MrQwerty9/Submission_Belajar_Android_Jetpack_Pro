package com.sstudio.submissionbajetpackpro.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieTvDao {

    @Query("SELECT * FROM MovieEntity")
    fun getAllMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM MovieEntity")
    fun getAllMovieList(): Flow<List<MovieEntity>>

    @Query("SELECT MovieEntity.*, FavoriteEntity.* FROM MovieEntity, FavoriteEntity WHERE MovieEntity.id = favoriteEntity.idMovieTv")
    fun getAllFavoriteMovie(): DataSource.Factory<Int, MovieFavorite>

    @Query("SELECT * FROM MovieEntity where id = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovie(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(data: MovieEntity)

    @Query("DELETE FROM MovieEntity")
    fun deleteAllMovie()

    @Query("SELECT * FROM TvEntity")
    fun getAllTv(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT *, * FROM TvEntity, FavoriteEntity WHERE TvEntity.id = favoriteEntity.idMovieTv")
    fun getAllFavoriteTv(): DataSource.Factory<Int, TvFavorite>

    @Query("SELECT * FROM TvEntity where id = :tvId")
    fun getTvById(tvId: Int): Flow<TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTv(tv: List<TvEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvDetail(tv: TvEntity)

    @Query("DELETE FROM TvEntity")
    fun deleteAllTv()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity where idMovieTv = :id")
    fun deleteFavorite(id: Int)

    @Query("SELECT * FROM FavoriteEntity where idMovieTv = :id")
    fun getFavoriteById(id: Int): Flow<List<FavoriteEntity>>
}