package com.sstudio.submissionbajetpackpro.ui.search.movie

import androidx.lifecycle.*
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.ui.search.SearchActivity

class SearchMovieViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    var listSearchMovie: LiveData<Resource<List<Movie>>> = Transformations.switchMap(SearchActivity.query){ query ->
        if (SearchActivity.query.value != "") {
            movieTvUseCase.getSearchMovie(query).asLiveData()
        }else{
            val emptyData: LiveData<Resource<List<Movie>>> = MutableLiveData()
            emptyData
        }
    }
}