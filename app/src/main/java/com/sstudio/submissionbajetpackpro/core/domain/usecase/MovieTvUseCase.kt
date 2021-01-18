package com.sstudio.submissionbajetpackpro.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.vo.Resource

interface MovieTvUseCase {
    fun getAllMovie(needFetch: Boolean): LiveData<Resource<PagedList<Movie>>>
    fun getMovieDetail(needFetch: Boolean, movieId: Int): LiveData<Resource<Movie>>
    fun getAllFavoriteMovie(): LiveData<PagedList<Movie>>

    fun getAllTvShows(needFetch: Boolean): LiveData<Resource<PagedList<Tv>>>
    fun getTvShowDetail(needFetch: Boolean, tvShowId: Int): LiveData<Resource<Tv>>
    fun getAllFavoriteTv(): LiveData<PagedList<Tv>>

    fun setFavorite(id: Int)
    fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>>
    fun deleteFavorite(id: Int)
}