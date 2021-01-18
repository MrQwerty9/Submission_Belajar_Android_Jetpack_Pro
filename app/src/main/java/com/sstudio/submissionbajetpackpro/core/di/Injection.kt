package com.sstudio.submissionbajetpackpro.core.di

import android.content.Context
import com.sstudio.submissionbajetpackpro.core.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.room.MovieTvDatabase
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.api.ApiConfig
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvInteractor
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.core.utils.AppExecutors

object Injection {
    private fun provideRepository(context: Context): MovieTvRepository {

        val database = MovieTvDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.getApiService(), context)
        val localDataSource = LocalDataSource.getInstance(database.movieTvDao())
        val appExecutors = AppExecutors()

        return MovieTvRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideMovieTvUseCase(context: Context): MovieTvUseCase {
        val repository = provideRepository(context)
        return MovieTvInteractor(repository)
    }
}
