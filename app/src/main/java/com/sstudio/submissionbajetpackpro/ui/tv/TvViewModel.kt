package com.sstudio.submissionbajetpackpro.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase

class TvViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    var listTvShow: LiveData<Resource<PagedList<Tv>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvUseCase.getAllTvShows(false).asLiveData()
            }
            return field
        }
        private set

    fun fetchListMovie(){
        listTvShow = movieTvUseCase.getAllTvShows(true).asLiveData()
    }
}