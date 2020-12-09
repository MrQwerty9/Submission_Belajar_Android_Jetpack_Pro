package com.sstudio.submissionbajetpackpro.data

import androidx.lifecycle.LiveData
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import java.util.*

interface MovieDataSource {

    fun getAllMovie(): LiveData<ArrayList<MovieEntity>>
    fun getAllTvShows(): LiveData<ArrayList<TvEntity>>

    fun getMovieDetail(movieId: Int): LiveData<MovieEntity>
    fun getTvShowDetail(tvShowId: Int): LiveData<TvEntity>
}