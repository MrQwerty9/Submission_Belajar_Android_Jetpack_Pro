package com.sstudio.submissionbajetpackpro.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.vo.Resource

class SearchViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    private var query: String = ""

    var listSearchMovie: LiveData<List<MovieEntity>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvRepository.getSearchMovie(query)
            }
            return field
        }
        private set

    var listSearchTv: LiveData<List<TvEntity>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvRepository.getSearchTv(query)
            }
            return field
        }
        private set

    fun setQuery(query: String){
        this.query = query
    }
}