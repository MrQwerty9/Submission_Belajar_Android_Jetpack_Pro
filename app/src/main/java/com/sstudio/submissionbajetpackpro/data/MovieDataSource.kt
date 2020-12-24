package com.sstudio.submissionbajetpackpro.data

import androidx.lifecycle.LiveData
import com.sstudio.submissionbajetpackpro.data.source.local.entity.*
import com.sstudio.submissionbajetpackpro.vo.Resource

interface MovieDataSource {

    fun getAllMovie(needFetch: Boolean): LiveData<Resource<List<MovieEntity>>>
    fun getMovieDetail(needFetch: Boolean, movieId: Int): LiveData<Resource<MovieEntity>>
    fun getAllFavoriteMovie(): LiveData<List<MovieFavorite>>

    fun getAllTvShows(needFetch: Boolean): LiveData<Resource<List<TvEntity>>>
    fun getTvShowDetail(needFetch: Boolean, tvShowId: Int): LiveData<Resource<TvEntity>>
    fun getAllFavoriteTv(): LiveData<List<TvFavorite>>

    fun setFavorite(id: Int)
    fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>>
    fun deleteFavoriteTv(id: Int)
}