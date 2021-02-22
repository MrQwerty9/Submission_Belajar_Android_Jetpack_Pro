package com.sstudio.submissionbajetpackpro.ui.search.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.ui.search.SearchActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class SearchMovieViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

//    val listSearchMovie: LiveData<Resource<List<Movie>>> = Transformations.switchMap(SearchActivity.queryChannel){
//        movieTvUseCase.getSearchMovie(it).asLiveData()
//    }

    suspend fun listSearchMovie(): LiveData<Resource<List<Movie>>> =
        SearchActivity.queryChannel.asFlow()
            .debounce(300)
            .distinctUntilChanged()
            .filter {
                it.trim().isNotEmpty()
            }
            .mapLatest {
                movieTvUseCase.getSearchMovie(it).asLiveData()
            }
            .first()
}