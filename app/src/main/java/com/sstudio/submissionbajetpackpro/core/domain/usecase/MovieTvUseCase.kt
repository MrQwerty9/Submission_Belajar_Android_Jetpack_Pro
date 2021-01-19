package com.sstudio.submissionbajetpackpro.core.domain.usecase

import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MovieTvUseCase {
    fun getAllMovie(needFetch: Boolean): Flow<Resource<PagedList<Movie>>>
    fun getMovieDetail(needFetch: Boolean, movieId: Int): Flow<Resource<Movie>>
    fun getAllFavoriteMovie(): Flow<PagedList<Movie>>

    fun getAllTvShows(needFetch: Boolean): Flow<Resource<PagedList<Tv>>>
    fun getTvShowDetail(needFetch: Boolean, tvShowId: Int): Flow<Resource<Tv>>
    fun getAllFavoriteTv(): Flow<PagedList<Tv>>

    fun setFavorite(id: Int)
    fun getFavoriteById(id: Int): Flow<List<FavoriteEntity>>
    fun deleteFavorite(id: Int)
}