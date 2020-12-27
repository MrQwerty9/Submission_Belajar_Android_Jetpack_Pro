package com.sstudio.submissionbajetpackpro.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.data.source.local.entity.*
import com.sstudio.submissionbajetpackpro.vo.Resource

interface MovieDataSource {

    fun getAllMovie(needFetch: Boolean): LiveData<Resource<PagedList<MovieEntity>>>
    fun getMovieDetail(needFetch: Boolean, movieId: Int): LiveData<Resource<MovieEntity>>
    fun getAllFavoriteMovie(): LiveData<PagedList<MovieFavorite>>

    fun getAllTvShows(needFetch: Boolean): LiveData<Resource<PagedList<TvEntity>>>
    fun getTvShowDetail(needFetch: Boolean, tvShowId: Int): LiveData<Resource<TvEntity>>
    fun getAllFavoriteTv(): LiveData<PagedList<TvFavorite>>

    fun setFavorite(id: Int)
    fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>>
    fun deleteFavoriteTv(id: Int)
}