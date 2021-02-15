package com.sstudio.submissionbajetpackpro.ui.detail.similarList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteMovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteTvEntity
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.ui.detail.DetailData

class SimilarViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    private val movieTvId = MutableLiveData<Int>()
    var needFetch = false

    fun setSelectedMovieTv(courseId: Int) {
        this.movieTvId.value = courseId
    }

    private fun setFavoriteMovie(id: Int) =
        movieTvUseCase.setFavoriteMovie(id)

    private fun setFavoriteTv(id: Int) =
        movieTvUseCase.setFavoriteTv(id)

    fun getFavoriteMovieStatus(id: Int): LiveData<List<FavoriteMovieEntity>> =
        movieTvUseCase.getFavoriteMovieById(id).asLiveData()

    fun getFavoriteTvStatus(id: Int): LiveData<List<FavoriteTvEntity>> =
        movieTvUseCase.getFavoriteTvById(id).asLiveData()

    private fun deleteFavoriteMovie(id: Int){
        movieTvUseCase.deleteFavoriteMovie(id)
    }

    private fun deleteFavoriteTv(id: Int){
        movieTvUseCase.deleteFavoriteTv(id)
    }

    fun updateFavorite(isFavorite: Boolean, movieOrTv: DetailData.Type, movieTvId: Int) {
        if (isFavorite) {
            if (movieOrTv == DetailData.Type.MOVIE)
                deleteFavoriteMovie(movieTvId)
            else
                deleteFavoriteTv(movieTvId)
        }
        else{
            if (movieOrTv == DetailData.Type.MOVIE)
                setFavoriteMovie(movieTvId)
            else
                setFavoriteTv(movieTvId)
        }
    }

}