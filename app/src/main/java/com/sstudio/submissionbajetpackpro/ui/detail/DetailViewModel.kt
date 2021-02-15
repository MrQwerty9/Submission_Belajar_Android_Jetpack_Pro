package com.sstudio.submissionbajetpackpro.ui.detail

import androidx.lifecycle.*
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.domain.model.*
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase

class DetailViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    private var movieTvId = 0
    lateinit var type: DetailData.Type
    var needFetch = false

    fun setSelectedMovieTv(courseId: Int, type: DetailData.Type) {
        this.movieTvId = courseId
        this.type = type
    }

    var detailMovie: LiveData<Resource<MovieDetail>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvUseCase.getMovieDetail(needFetch, movieTvId).asLiveData()
            }
            return field
        }
        private set

    var detailTv: LiveData<Resource<TvDetail>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvUseCase.getTvShowDetail(needFetch, movieTvId).asLiveData()
            }
            return field
        }

    private fun setFavorite() {
        if (type == DetailData.Type.MOVIE)
            movieTvUseCase.setFavoriteMovie(movieTvId)
        else
            movieTvUseCase.setFavoriteTv(movieTvId)
    }

    fun getFavoriteStatus(): LiveData<Boolean> {
        val favorite =
            if (type == DetailData.Type.MOVIE)
                movieTvUseCase.getFavoriteMovieById(movieTvId).asLiveData()
            else
                movieTvUseCase.getFavoriteTvById(movieTvId).asLiveData()
        return Transformations.map(favorite) {
            it.isNotEmpty()
        }
    }

    private fun deleteFavorite() {
        if (type == DetailData.Type.MOVIE)
            movieTvUseCase.deleteFavoriteMovie(movieTvId)
        else
            movieTvUseCase.deleteFavoriteTv(movieTvId)
    }

    fun updateFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            deleteFavorite()
        } else {
            setFavorite()
        }
    }

    var getCredits: LiveData<Resource<Credits>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = fetchCredits()
            }
            return field
        }
        private set

    fun fetchCredits(): LiveData<Resource<Credits>> =
        if (type == DetailData.Type.MOVIE)
            movieTvUseCase.getCreditsMovie(movieTvId).asLiveData()
        else
            movieTvUseCase.getCreditsTv(movieTvId).asLiveData()

    var getSimilarMovie: LiveData<Resource<List<Movie>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvUseCase.getSimilarMovie(movieTvId).asLiveData()
            }
            return field
        }
        private set

    var getSimilarTv: LiveData<Resource<List<Tv>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = movieTvUseCase.getSimilarTv(movieTvId).asLiveData()
            }
            return field
        }
        private set

    fun fetchSimilar() =
        if (type == DetailData.Type.MOVIE)
            movieTvUseCase.getSimilarMovie(movieTvId).asLiveData()
        else
            movieTvUseCase.getSimilarTv(movieTvId).asLiveData()

    var getVideo: LiveData<Resource<List<Video>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = fetchVideo()
            }
            return field
        }
        private set

    private fun fetchVideo(): LiveData<Resource<List<Video>>> =
        if (type == DetailData.Type.MOVIE)
            movieTvUseCase.getVideoMovie(movieTvId).asLiveData()
        else
            movieTvUseCase.getVideoTv(movieTvId).asLiveData()
}