package com.sstudio.submissionbajetpackpro.ui.tv.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.core.utils.Params

class TvListViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    private val movieParams = MutableLiveData<Params.MovieParams>()
    private val repoResult = Transformations.map(movieParams){
        movieTvUseCase.getTvShowsList(it)
    }

    val state = Transformations.switchMap(repoResult){
        it.state
    }
    var listTv: LiveData<PagedList<Tv>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = Transformations.switchMap(repoResult){
                    it.data
                }
            }
            return field
        }
        private set


    fun setTv(params: Params.MovieParams?) {
        movieParams.postValue(params)
    }
}