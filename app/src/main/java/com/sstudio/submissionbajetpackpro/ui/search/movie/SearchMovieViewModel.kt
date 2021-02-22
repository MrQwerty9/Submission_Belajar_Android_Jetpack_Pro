package com.sstudio.submissionbajetpackpro.ui.search.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.ui.search.SearchActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class SearchMovieViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    val listSearchMovie: LiveData<Resource<List<Movie>>> = Transformations.map(SearchActivity.queryChannel){
        movieTvUseCase.getSearchMovie(it)
    }
//    val listSearchMovie: LiveData<Resource<List<Movie>>> = SearchActivity.queryChannel.asFlow()
//        .debounce(300)
//        .distinctUntilChanged()
//        .filter {
//            it.trim().isNotEmpty()
//        }
//        .mapLatest {
//            Log.d("mytag", "queryViewModel $it")
//            movieTvUseCase.getSearchMovie(it).first()
//        }
//        .asLiveData()
}