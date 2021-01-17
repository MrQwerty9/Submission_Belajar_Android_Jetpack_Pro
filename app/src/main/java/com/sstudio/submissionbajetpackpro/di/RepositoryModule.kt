package com.sstudio.submissionbajetpackpro.di

import com.sstudio.submissionbajetpackpro.data.MovieDataSource
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(movieTvRepository: MovieTvRepository): MovieDataSource

}