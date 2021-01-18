package com.sstudio.submissionbajetpackpro.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.vo.Resource

class DetailViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {

    private val movieTvId = MutableLiveData<Int>()
    var needFetch = false

    fun setSelectedMovieTv(courseId: Int) {
        this.movieTvId.value = courseId
    }

    var detailMovie: LiveData<Resource<Movie>> = Transformations.switchMap(movieTvId) { mMovieTvId ->
        movieTvUseCase.getMovieDetail(needFetch, mMovieTvId)
    }

    var detailTv: LiveData<Resource<Tv>> = Transformations.switchMap(movieTvId) { mMovieTvId ->
        movieTvUseCase.getTvShowDetail(needFetch, mMovieTvId)
    }

    fun setFavorite(id: Int) =
        movieTvUseCase.setFavorite(id)

    fun getFavoriteStatus(id: Int): LiveData<List<FavoriteEntity>> =
        movieTvUseCase.getFavoriteById(id)

    fun deleteFavorite(id: Int){
        movieTvUseCase.deleteFavorite(id)
    }
}