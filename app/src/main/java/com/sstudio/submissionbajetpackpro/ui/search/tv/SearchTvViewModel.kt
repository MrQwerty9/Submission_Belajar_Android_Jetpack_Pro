package com.sstudio.submissionbajetpackpro.ui.search.tv

import androidx.lifecycle.*
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.ui.search.SearchActivity

class SearchTvViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    var listSearchTv: LiveData<Resource<List<Tv>>> = Transformations.switchMap(SearchActivity.query){ query ->
        if (SearchActivity.query.value != "") {
            movieTvUseCase.getSearchTv(query).asLiveData()
        }else{
            val emptyData: LiveData<Resource<List<Tv>>> = MutableLiveData()
            emptyData
        }
    }
}