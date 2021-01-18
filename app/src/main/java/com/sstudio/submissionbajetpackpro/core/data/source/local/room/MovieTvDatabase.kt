package com.sstudio.submissionbajetpackpro.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.TvEntity

@Database(entities = [MovieEntity::class, TvEntity::class, FavoriteEntity::class],
    version = 2,
    exportSchema = false)
abstract class MovieTvDatabase : RoomDatabase() {
    abstract fun movieTvDao(): MovieTvDao

    companion object{
        @Volatile
        private var INSTANCE: MovieTvDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `FavoriteEntity` (`idMovieTv` INTEGER NOT NULL, PRIMARY KEY(`idMovieTv`))")
                database.execSQL("INSERT INTO FavoriteEntity(idMovieTv) SELECT idMovieTv FROM favorite_entities")
                database.execSQL("DROP TABLE favorite_entities")

//                database.execSQL("CREATE TABLE MovieEntity(backdropPath STRING, id INTEGER, genreIds STRING, " +
//                        "originalTitle STRING, overview STRING, posterPath String, releaseDate STRING, voteAverage DOUBLE, PRIMARY KEY(id))")
                database.execSQL("CREATE TABLE IF NOT EXISTS `MovieEntity` (`backdropPath` TEXT NOT NULL, `genreIds` TEXT NOT NULL, `id` INTEGER NOT NULL, `originalTitle` TEXT NOT NULL, `overview` TEXT NOT NULL, `posterPath` TEXT NOT NULL, `releaseDate` TEXT NOT NULL, `voteAverage` REAL NOT NULL, PRIMARY KEY(`id`))")
                database.execSQL("DROP TABLE movie_entities")
//
//                database.execSQL("CREATE TABLE TvEntity(backdropPath STRING, firstAirDate STRING, id INTEGER, genreIds STRING, " +
//                        "originalName STRING, overview STRING, posterPath String, voteAverage DOUBLE, PRIMARY KEY(id))")
                database.execSQL("CREATE TABLE IF NOT EXISTS `TvEntity` (`backdropPath` TEXT NOT NULL, `firstAirDate` TEXT NOT NULL, `genreIds` TEXT NOT NULL, `id` INTEGER NOT NULL, `originalName` TEXT NOT NULL, `overview` TEXT NOT NULL, `posterPath` TEXT NOT NULL, `voteAverage` REAL NOT NULL, PRIMARY KEY(`id`))")
                database.execSQL("DROP TABLE tv_entities")
            }

        }

        fun getInstance(context: Context): MovieTvDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    MovieTvDatabase::class.java,
                    "MovieTvDb.db")
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
    }


}