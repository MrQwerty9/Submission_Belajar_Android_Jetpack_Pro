package com.sstudio.submissionbajetpackpro.di

import android.content.Context
import androidx.room.Room
import com.sstudio.submissionbajetpackpro.data.source.local.room.MovieTvDao
import com.sstudio.submissionbajetpackpro.data.source.local.room.MovieTvDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): MovieTvDatabase = Room.databaseBuilder(
        context,
        MovieTvDatabase::class.java, "MovieTvDb.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideTourismDao(database: MovieTvDatabase): MovieTvDao = database.movieTvDao()
}