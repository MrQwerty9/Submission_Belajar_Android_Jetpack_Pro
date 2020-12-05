package com.sstudio.submissionbajetpackpro.ui.movie

import androidx.lifecycle.ViewModel
import com.sstudio.submissionbajetpackpro.data.MovieTvEntity
import com.sstudio.submissionbajetpackpro.utils.DataDummy

class MovieViewModel : ViewModel() {
    fun getMovies(): List<MovieTvEntity> = DataDummy.generateDummyMovies()
}