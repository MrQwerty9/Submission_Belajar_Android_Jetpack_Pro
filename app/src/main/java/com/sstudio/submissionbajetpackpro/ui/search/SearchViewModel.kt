package com.sstudio.submissionbajetpackpro.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sstudio.submissionbajetpackpro.core.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv

class SearchViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    private var query: String = ""

    var listSearchMovie: LiveData<List<Movie>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvRepository.getSearchMovie(query)
            }
            return field
        }
        private set

    var listSearchTv: LiveData<List<Tv>>? = null
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