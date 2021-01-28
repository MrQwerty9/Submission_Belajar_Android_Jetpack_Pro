package com.sstudio.submissionbajetpackpro.core.data.source.remote.paging.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse


class MovieDataSourceFactory(private val remoteDataSource: RemoteDataSource) : DataSource.Factory<Long, ApiResponse<MovieResponse>>() {
    var movieDataSource: MovieDataSource? = null
    var mutableLiveData: MutableLiveData<MovieDataSource> = MutableLiveData()

    override fun create(): DataSource<Long, ApiResponse<MovieResponse>> {
        movieDataSource = MovieDataSource(remoteDataSource)
        mutableLiveData.postValue(movieDataSource)
        return movieDataSource as MovieDataSource
    }
}