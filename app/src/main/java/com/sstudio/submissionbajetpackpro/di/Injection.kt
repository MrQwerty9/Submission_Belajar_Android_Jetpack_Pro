package com.sstudio.submissionbajetpackpro.di

import android.content.Context
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.data.source.remote.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): MovieTvRepository {

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.getApiService(), context)

        return MovieTvRepository.getInstance(remoteDataSource)
    }
}
