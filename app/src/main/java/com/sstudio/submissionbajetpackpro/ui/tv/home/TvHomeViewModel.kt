package com.sstudio.submissionbajetpackpro.ui.tv.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.domain.model.Genre
import com.sstudio.submissionbajetpackpro.core.domain.model.TvHome
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase

class TvHomeViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    var listTv: LiveData<List<Resource<TvHome>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvUseCase.getTvHome().asLiveData()
            }
            return field
        }
        private set


    fun refresh() {
        listTv = movieTvUseCase.getTvHome().asLiveData()
    }

    var listAllGenre: LiveData<Resource<List<Genre>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvUseCase.getAllGenreTv().asLiveData()
            }
            return field
        }
        private set
}