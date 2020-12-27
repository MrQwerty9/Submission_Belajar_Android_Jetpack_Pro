package com.sstudio.submissionbajetpackpro.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.vo.Resource

class TvViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    var listTvShow: LiveData<Resource<PagedList<TvEntity>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvRepository.getAllTvShows(false)
            }
            return field
        }
        private set

    fun fetchListMovie(){
        listTvShow = movieTvRepository.getAllTvShows(true)
    }
}