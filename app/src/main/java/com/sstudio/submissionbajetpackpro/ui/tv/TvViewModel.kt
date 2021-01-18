package com.sstudio.submissionbajetpackpro.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.vo.Resource

class TvViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    var listTvShow: LiveData<Resource<PagedList<Tv>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvUseCase.getAllTvShows(false)
            }
            return field
        }
        private set

    fun fetchListMovie(){
        listTvShow = movieTvUseCase.getAllTvShows(true)
    }
}