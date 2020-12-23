package com.sstudio.submissionbajetpackpro.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sstudio.submissionbajetpackpro.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity

@Database(entities = [MovieEntity::class, TvEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false)
abstract class MovieTvDatabase : RoomDatabase() {
    abstract fun movieTvDao(): MovieTvDao

    companion object{
        @Volatile
        private var INSTANCE: MovieTvDatabase? = null

        fun getInstance(context: Context): MovieTvDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    MovieTvDatabase::class.java,
                    "MovieTvDb.db").build()
            }
    }
}