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

    @Query("SELECT MovieEntity.*, ListTypeEntity.* FROM MovieEntity, ListTypeEntity WHERE ListTypeEntity.listType = :listType AND MovieEntity.idMovie = ListTypeEntity.idMovieTv")
    fun getMovieListType(listType: String): Flow<List<MovieEntity>>

    @Query("SELECT MovieEntity.*, ListTypeEntity.* FROM MovieEntity, ListTypeEntity WHERE ListTypeEntity.listType = :listType AND MovieEntity.idMovie = ListTypeEntity.idMovieTv")
    fun getMovieListTypePaging(listType: String): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT MovieEntity.*, FavoriteEntity.* FROM MovieEntity, FavoriteEntity WHERE MovieEntity.idMovie = favoriteEntity.idMovieTv")
    fun getAllFavoriteMovie(): DataSource.Factory<Int, MovieFavorite>

    @Query("SELECT * FROM MovieEntity where idMovie = :movieId")
    fun getMovieById(movieId: Int): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovie(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(data: MovieEntity)

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteAllMovie()


    @Query("SELECT TvEntity.*, ListTypeEntity.* FROM TvEntity, ListTypeEntity WHERE ListTypeEntity.listType = :listType AND TvEntity.idTv = ListTypeEntity.idMovieTv")
    fun getTvListType(listType: String): Flow<List<TvEntity>>

    @Query("SELECT TvEntity.*, ListTypeEntity.* FROM TvEntity, ListTypeEntity WHERE ListTypeEntity.listType = :listType AND TvEntity.idTv = ListTypeEntity.idMovieTv")
    fun getTvListTypePaging(listType: String): DataSource.Factory<Int, TvEntity>

    @Query("SELECT *, * FROM TvEntity, FavoriteEntity WHERE TvEntity.idTv = favoriteEntity.idMovieTv")
    fun getAllFavoriteTv(): DataSource.Factory<Int, TvFavorite>

    @Query("SELECT * FROM TvEntity where idTv = :tvId")
    fun getTvById(tvId: Int): Flow<List<TvEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTv(tv: List<TvEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvDetail(tv: TvEntity)

    @Query("DELETE FROM TvEntity")
    suspend fun deleteAllTv()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity where idMovieTv = :id")
    fun deleteFavorite(id: Int)

    @Query("SELECT * FROM FavoriteEntity where idMovieTv = :id")
    fun getFavoriteById(id: Int): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListType(listTypeEntity: List<ListTypeEntity>)

    @Query("DELETE FROM ListTypeEntity where listType = :listType")
    suspend fun deleteListType(listType: String)
}