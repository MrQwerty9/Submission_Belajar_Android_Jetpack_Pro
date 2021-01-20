package com.sstudio.submissionbajetpackpro.favorite.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase

class FavoriteTvShowViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    var listTv: LiveData<PagedList<Tv>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvUseCase.getAllFavoriteTv().asLiveData()
            }
            return field
        }
        private set

    fun deleteFavorite(idMovieTv: Int){
        movieTvUseCase.deleteFavorite(idMovieTv)
    }

    fun addFavorite(idMovieTv: Int) {
        movieTvUseCase.setFavorite(idMovieTv)
    }
}