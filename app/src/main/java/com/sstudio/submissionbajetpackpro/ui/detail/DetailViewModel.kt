package com.sstudio.submissionbajetpackpro.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity

class DetailViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    private var movieTvId: Int = 0
    var detailMovie: LiveData<MovieEntity>? = null
    var detailTv: LiveData<TvEntity>? = null

    fun setSelectedMovieTv(courseId: Int) {
        this.movieTvId = courseId
    }

    fun getMovie() {
        detailMovie = movieTvRepository.getMovieDetail(movieTvId)
    }

    fun getTv() {
        detailTv = movieTvRepository.getTvShowDetail(movieTvId)
    }
}