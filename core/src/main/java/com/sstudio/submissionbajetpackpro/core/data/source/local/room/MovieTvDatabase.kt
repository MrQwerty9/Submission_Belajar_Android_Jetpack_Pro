package com.sstudio.submissionbajetpackpro.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.ListTypeEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.TvEntity

@Database(entities = [MovieEntity::class, TvEntity::class, FavoriteEntity::class, ListTypeEntity::class],
    version = 7,
    exportSchema = false)
abstract class MovieTvDatabase : RoomDatabase() {
    abstract fun movieTvDao(): MovieTvDao
}