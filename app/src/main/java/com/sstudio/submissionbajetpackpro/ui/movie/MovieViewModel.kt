package com.sstudio.submissionbajetpackpro.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.vo.Resource

class MovieViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    var listMovie: LiveData<Resource<PagedList<MovieEntity>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvRepository.getAllMovie(false)
            }
            return field
        }
        private set

    fun fetchListMovie(){
        listMovie = movieTvRepository.getAllMovie(true)
    }
}