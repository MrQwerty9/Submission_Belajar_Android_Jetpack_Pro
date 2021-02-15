package com.sstudio.submissionbajetpackpro.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.*

@Database(entities = [MovieEntity::class, TvEntity::class, FavoriteMovieEntity::class,
    FavoriteTvEntity::class, MovieListEntity::class, TvListEntity::class,
                     MovieDetailEntity::class, TvDetailEntity::class],
    version = 10,
    exportSchema = false)
abstract class MovieTvDatabase : RoomDatabase() {
    abstract fun movieTvDao(): MovieTvDao

    companion object{
        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `ListTypeEntity_New` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `idMovieTv` INTEGER NOT NULL, `listType` TEXT NOT NULL)"
                )
                database.execSQL(
                    "INSERT INTO ListTypeEntity_New (id, idMovieTv, listType) SELECT id, idMovieTv, listType FROM ListTypeEntity"
                )
                database.execSQL("DROP TABLE ListTypeEntity")
                database.execSQL("ALTER TABLE ListTypeEntity_New RENAME TO ListTypeEntity")
            }
        }
        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `TvEntity_new` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `backdropPath` TEXT NOT NULL, `firstAirDate` TEXT NOT NULL, `genreIds` TEXT NOT NULL, `idTv` INTEGER NOT NULL, `originalName` TEXT NOT NULL, `overview` TEXT NOT NULL, `posterPath` TEXT NOT NULL, `voteAverage` REAL NOT NULL)"
                )
                database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_TvEntity_idTv` ON `TvEntity_new` (`idTv`)")
                database.execSQL(
                    "INSERT OR REPLACE INTO TvEntity_new (id, backdropPath, firstAirDate, genreIds, idTv, originalName, overview, posterPath, voteAverage) SELECT id, backdropPath, firstAirDate, genreIds, idTv, originalName, overview, posterPath, voteAverage FROM TvEntity"
                )
                database.execSQL("DROP TABLE TvEntity")
                database.execSQL("ALTER TABLE TvEntity_new RENAME TO TvEntity")
            }
        }
        val MIGRATION_7_8 = object : Migration(7, 8) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `ListTypeMovieEntity` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `idMovieTv` INTEGER NOT NULL, `listType` TEXT NOT NULL)"
                )
                database.execSQL("ALTER TABLE ListTypeEntity RENAME TO ListTypeTvEntity")
            }
        }
    }
}