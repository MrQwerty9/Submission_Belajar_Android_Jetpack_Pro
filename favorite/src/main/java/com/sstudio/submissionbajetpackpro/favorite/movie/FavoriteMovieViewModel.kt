package com.sstudio.submissionbajetpackpro.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase

class FavoriteMovieViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    var listMovie: LiveData<PagedList<Movie>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvUseCase.getAllFavoriteMovie().asLiveData()

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