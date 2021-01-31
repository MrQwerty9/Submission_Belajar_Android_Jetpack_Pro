package com.sstudio.submissionbajetpackpro.core.data.source.remote.paging.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.core.utils.AppExecutors


class MovieDataSourceFactory(private val remoteDataSource: RemoteDataSource,
                             private val localDataSource: LocalDataSource,
                             private val appExecutors: AppExecutors
) : DataSource.Factory<Long, MovieResponse.Result>() {

    var movieDataSource: MovieDataSource? = null
    var mutableLiveData: MutableLiveData<MovieDataSource> = MutableLiveData()

    override fun create(): DataSource<Long, MovieResponse.Result> {
        movieDataSource = MovieDataSource(remoteDataSource, localDataSource, appExecutors)
        mutableLiveData.postValue(movieDataSource)
        return movieDataSource as MovieDataSource
    }
}