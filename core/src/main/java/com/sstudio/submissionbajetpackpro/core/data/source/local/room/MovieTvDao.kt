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

    @Query("SELECT MovieEntity.*, ListTypeMovieEntity.* FROM MovieEntity, ListTypeMovieEntity WHERE ListTypeMovieEntity.listType = :listType AND MovieEntity.idMovie = ListTypeMovieEntity.idMovie")
    fun getMovieListType(listType: String): Flow<List<MovieEntity>>

    @Query("SELECT MovieEntity.*, ListTypeMovieEntity.* FROM MovieEntity, ListTypeMovieEntity WHERE ListTypeMovieEntity.listType = :listType AND MovieEntity.idMovie = ListTypeMovieEntity.idMovie")
    fun getMovieListTypePaging(listType: String): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT MovieEntity.*, FavoriteMovieEntity.* FROM MovieEntity, FavoriteMovieEntity WHERE MovieEntity.idMovie = FavoriteMovieEntity.idMovie")
    fun getAllFavoriteMovie(): DataSource.Factory<Int, MovieFavorite>

    @Query("SELECT * FROM MovieEntity where idMovie = :movieId")
    fun getMovieById(movieId: Int): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(data: MovieEntity)

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteAllMovie()


    @Query("SELECT TvEntity.*, ListTypeTvEntity.* FROM TvEntity, ListTypeTvEntity WHERE ListTypeTvEntity.listType = :listType AND TvEntity.idTv = ListTypeTvEntity.idTv")
    fun getTvListType(listType: String): Flow<List<TvEntity>>

    @Query("SELECT TvEntity.*, ListTypeTvEntity.* FROM TvEntity, ListTypeTvEntity WHERE ListTypeTvEntity.listType = :listType AND TvEntity.idTv = ListTypeTvEntity.idTv")
    fun getTvListTypePaging(listType: String): DataSource.Factory<Int, TvEntity>

    @Query("SELECT *, * FROM TvEntity, FavoriteTvEntity WHERE TvEntity.idTv = FavoriteTvEntity.idTv")
    fun getAllFavoriteTv(): DataSource.Factory<Int, TvFavorite>

    @Query("SELECT * FROM TvEntity where idTv = :tvId")
    fun getTvById(tvId: Int): Flow<List<TvEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tv: List<TvEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvDetail(tv: TvEntity)

    @Query("DELETE FROM TvEntity")
    suspend fun deleteAllTv()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(favorite: FavoriteMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteTv(favorite: FavoriteTvEntity)

    @Query("DELETE FROM FavoriteMovieEntity where idMovie = :id")
    fun deleteFavoriteMovie(id: Int)

    @Query("DELETE FROM FavoriteTvEntity where idTv = :id")
    fun deleteFavoriteTv(id: Int)

    @Query("SELECT * FROM FavoriteMovieEntity where idMovie = :id")
    fun getFavoriteMovieById(id: Int): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT * FROM FavoriteTvEntity where idTv = :id")
    fun getFavoriteTvById(id: Int): Flow<List<FavoriteTvEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieListType(listTypeEntity: List<ListTypeMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvListType(listTypeEntity: List<ListTypeTvEntity>)

    @Query("DELETE FROM ListTypeMovieEntity where listType = :listType")
    suspend fun deleteMovieList(listType: String)

    @Query("DELETE FROM ListTypeTvEntity where listType = :listType")
    suspend fun deleteTvList(listType: String)
}