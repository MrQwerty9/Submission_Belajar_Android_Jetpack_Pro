package com.sstudio.submissionbajetpackpro.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity

class MovieViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    var listMovie: LiveData<ArrayList<MovieEntity>>? = null

    fun getAllMovies(){
        listMovie = movieTvRepository.getAllMovie()
    }
}