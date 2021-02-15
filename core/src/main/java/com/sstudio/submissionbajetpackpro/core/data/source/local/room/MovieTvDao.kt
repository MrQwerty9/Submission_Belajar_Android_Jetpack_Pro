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

    @Query("SELECT MovieEntity.*, MovieListEntity.* FROM MovieEntity, MovieListEntity WHERE MovieListEntity.listType = :listType AND MovieEntity.id_movie = MovieListEntity.id_movie")
    fun getMovieListType(listType: String): Flow<List<MovieEntity>>

    @Query("SELECT MovieEntity.*, MovieListEntity.* FROM MovieEntity, MovieListEntity WHERE MovieListEntity.listType = :listType AND MovieEntity.id_movie = MovieListEntity.id_movie")
    fun getMovieListTypePaging(listType: String): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT MovieEntity.*, FavoriteMovieEntity.* FROM MovieEntity, FavoriteMovieEntity WHERE MovieEntity.id_movie = FavoriteMovieEntity.idMovie")
    fun getAllFavoriteMovie(): DataSource.Factory<Int, MovieFavorite>

    @Query("SELECT MovieEntity.*, MovieDetailEntity.* FROM MovieEntity, MovieDetailEntity WHERE MovieEntity.id_movie = MovieDetailEntity.id_movie AND MovieDetailEntity.id_movie = :idMovie")
    fun getMovieDetail(idMovie: Int): Flow<MovieDetailEmbed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(data: MovieDetailEntity)

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteAllMovie()


    @Query("SELECT TvEntity.*, TvListEntity.* FROM TvEntity, TvListEntity WHERE TvListEntity.listType = :listType AND TvEntity.id_tv = TvListEntity.id_tv")
    fun getTvListType(listType: String): Flow<List<TvEntity>>

    @Query("SELECT TvEntity.*, TvListEntity.* FROM TvEntity, TvListEntity WHERE TvListEntity.listType = :listType AND TvEntity.id_tv = TvListEntity.id_tv")
    fun getTvListTypePaging(listType: String): DataSource.Factory<Int, TvEntity>

    @Query("SELECT *, * FROM TvEntity, FavoriteTvEntity WHERE TvEntity.id_tv = FavoriteTvEntity.idTv")
    fun getAllFavoriteTv(): DataSource.Factory<Int, TvFavorite>

    @Query("SELECT TvEntity.*, TvDetailEntity.* FROM TvEntity, TvDetailEntity WHERE TvEntity.id_tv = TvDetailEntity.id_tv AND TvDetailEntity.id_tv = :idTv")
    fun getTvDetail(idTv: Int): Flow<TvDetailEmbed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tv: List<TvEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvDetail(tv: TvDetailEntity)

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
    suspend fun insertMovieListType(listEntity: List<MovieListEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvListType(listTypeEntity: List<TvListEntity>)

    @Query("DELETE FROM MovieListEntity where listType = :listType")
    suspend fun deleteMovieList(listType: String)

    @Query("DELETE FROM TvListEntity where listType = :listType")
    suspend fun deleteTvList(listType: String)
}