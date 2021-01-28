package com.sstudio.submissionbajetpackpro.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.core.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.paging.movie.MovieDataSourceFactory
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie

class MovieViewModel(private val movieTvUseCase: MovieTvRepository, remoteDataSource: RemoteDataSource) : ViewModel() {

    private val movieDataSourceFactory = MovieDataSourceFactory(remoteDataSource)
//    val state = Transformations.switchMap(
//        movieDataSourceFactory.mutableLiveData,
//        MovieDataSource::state
//    )

    var listMovie: LiveData<ApiResponse<PagedList<Movie>>>? = null
        get() {
            if (field == null){
                field = MutableLiveData()
                field = movieTvUseCase.getAllMovie(false, movieDataSourceFactory).asLiveData()
            }
            return field
        }
        private set

    fun fetchListMovie(){
        listMovie = movieTvUseCase.getAllMovie(true, movieDataSourceFactory).asLiveData()
    }
}