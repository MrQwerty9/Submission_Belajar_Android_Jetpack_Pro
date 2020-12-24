package com.sstudio.submissionbajetpackpro.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.vo.Resource

class DetailViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    val movieTvId = MutableLiveData<Int>()
    var needFetch = false

    fun setSelectedMovieTv(courseId: Int) {
        this.movieTvId.value = courseId
    }

    var detailMovie: LiveData<Resource<MovieEntity>> = Transformations.switchMap(movieTvId) { mMovieTvId ->
        movieTvRepository.getMovieDetail(needFetch, mMovieTvId)
    }

    var detailTv: LiveData<Resource<TvEntity>> = Transformations.switchMap(movieTvId) { mMovieTvId ->
        movieTvRepository.getTvShowDetail(needFetch, mMovieTvId)
    }

    fun setFavorite(id: Int) =
        movieTvRepository.setFavorite(id)

    fun getFavoriteStatus(id: Int): LiveData<List<FavoriteEntity>> =
        movieTvRepository.getFavoriteById(id)

    fun deleteFavorite(id: Int){
        movieTvRepository.deleteFavoriteTv(id)
    }

//    fun getMovie() {
//        detailMovie = movieTvRepository.getMovieDetail(movieTvId)
//    }

//    fun getTv() {
//        detailTv = movieTvRepository.getTvShowDetail(movieTvId)
//    }
}