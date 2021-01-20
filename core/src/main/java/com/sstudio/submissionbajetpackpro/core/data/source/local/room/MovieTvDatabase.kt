package com.sstudio.submissionbajetpackpro.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.TvEntity

@Database(entities = [MovieEntity::class, TvEntity::class, FavoriteEntity::class],
    version = 2,
    exportSchema = false)
abstract class MovieTvDatabase : RoomDatabase() {
    abstract fun movieTvDao(): MovieTvDao
}