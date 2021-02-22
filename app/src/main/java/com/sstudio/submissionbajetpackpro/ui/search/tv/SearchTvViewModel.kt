package com.sstudio.submissionbajetpackpro.ui.search.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.ui.search.SearchActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class SearchTvViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    suspend fun listSearchTv(): LiveData<Resource<List<Tv>>> =
        SearchActivity.queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            movieTvUseCase.getSearchTv(it).asLiveData()
        }
        .first()
}