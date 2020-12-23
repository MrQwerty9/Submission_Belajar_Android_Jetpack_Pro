package com.sstudio.submissionbajetpackpro.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieFavorite

class FavoriteMovieViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    var listMovie: LiveData<List<MovieFavorite>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvRepository.getAllFavoriteMovie()
            }
            return field
        }
        private set
}