package com.sstudio.submissionbajetpackpro.data

import androidx.lifecycle.LiveData
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieFavorite
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvFavorite
import com.sstudio.submissionbajetpackpro.vo.Resource

interface MovieDataSource {

    fun getAllMovie(): LiveData<Resource<List<MovieEntity>>>
    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>>
    fun getAllFavoriteMovie(): LiveData<List<MovieFavorite>>
    fun setFavoriteMovie(movieEntity: MovieEntity)

    fun getAllTvShows(): LiveData<Resource<List<TvEntity>>>
    fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvEntity>>
    fun getAllFavoriteTv(): LiveData<List<TvFavorite>>
    fun setFavoriteTv(tvEntity: TvEntity)
}