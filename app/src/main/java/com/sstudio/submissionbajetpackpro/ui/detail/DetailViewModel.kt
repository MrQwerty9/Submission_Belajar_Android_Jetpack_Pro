package com.sstudio.submissionbajetpackpro.ui.detail

import androidx.lifecycle.*
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteMovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteTvEntity
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase

class DetailViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    private val movieTvId = MutableLiveData<Int>()
    var needFetch = false

    fun setSelectedMovieTv(courseId: Int) {
        this.movieTvId.value = courseId
    }

    var detailMovie: LiveData<Resource<Movie>> = Transformations.switchMap(movieTvId) { mMovieTvId ->
        movieTvUseCase.getMovieDetail(needFetch, mMovieTvId).asLiveData()
    }

    var detailTv: LiveData<Resource<Tv>> = Transformations.switchMap(movieTvId) { mMovieTvId ->
        movieTvUseCase.getTvShowDetail(needFetch, mMovieTvId).asLiveData()
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

    fun updateFavorite(isFavorite: Boolean, movieOrTv: String?, movieTvId: Int) {
        if (isFavorite) {
            if (movieOrTv == DetailActivity.IS_MOVIE)
                deleteFavoriteMovie(movieTvId)
            else
                deleteFavoriteTv(movieTvId)
        }
        else{
            if (movieOrTv == DetailActivity.IS_MOVIE)
                setFavoriteMovie(movieTvId)
            else
                setFavoriteTv(movieTvId)
        }
    }
}