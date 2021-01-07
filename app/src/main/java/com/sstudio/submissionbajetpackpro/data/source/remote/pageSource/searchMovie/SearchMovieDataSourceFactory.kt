package com.sstudio.submissionbajetpackpro.data.source.remote.pageSource.searchMovie

import androidx.paging.DataSource
import com.sstudio.submissionbajetpackpro.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.data.source.remote.response.MovieResponse

class SearchMovieDataSourceFactory (private val remoteDataSource: RemoteDataSource) : DataSource.Factory<Int, MovieResponse.Result>() {

    override fun create(): DataSource<Int, MovieResponse.Result> {
        return remoteDataSource.getSearchMovie()
    }
}