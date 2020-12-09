package com.sstudio.submissionbajetpackpro.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity

class TvViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    var listTvShow: LiveData<ArrayList<TvEntity>>? = null

    fun getAllTvShows(){
        listTvShow = movieTvRepository.getAllTvShows()
    }
}