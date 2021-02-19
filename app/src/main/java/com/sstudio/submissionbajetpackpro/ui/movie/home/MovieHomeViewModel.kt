package com.sstudio.submissionbajetpackpro.ui.movie.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.domain.model.Genre
import com.sstudio.submissionbajetpackpro.core.domain.model.MovieHome
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase

class MovieHomeViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    var listMovie: LiveData<List<Resource<MovieHome>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvUseCase.getMovieHome().asLiveData()
            }
            return field
        }
        private set


    fun refresh() {
        listMovie = movieTvUseCase.getMovieHome().asLiveData()
    }

    var listAllGenre: LiveData<Resource<List<Genre>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvUseCase.getAllGenreMovie().asLiveData()
            }
            return field
        }
        private set
}