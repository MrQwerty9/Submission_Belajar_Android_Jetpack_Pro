package com.sstudio.submissionbajetpackpro.ui.favorite.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvFavorite

class FavoriteTvShowViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    var listTv: LiveData<PagedList<TvFavorite>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvRepository.getAllFavoriteTv()
            }
            return field
        }
        private set
}