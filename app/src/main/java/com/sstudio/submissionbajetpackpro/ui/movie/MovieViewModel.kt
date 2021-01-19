package com.sstudio.submissionbajetpackpro.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.vo.Resource

class MovieViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    var listMovie: LiveData<Resource<PagedList<Movie>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvUseCase.getAllMovie(false).asLiveData()
            }
            return field
        }
        private set

    fun fetchListMovie(){
        listMovie = movieTvUseCase.getAllMovie(true).asLiveData()
    }
}