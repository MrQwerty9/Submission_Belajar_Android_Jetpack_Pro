package com.sstudio.submissionbajetpackpro.core.domain.usecase

import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteMovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteTvEntity
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RepoResult
import com.sstudio.submissionbajetpackpro.core.domain.model.*
import com.sstudio.submissionbajetpackpro.core.utils.Params
import kotlinx.coroutines.flow.Flow

interface MovieTvUseCase {
    fun getMovieHome(): Flow<List<Resource<MovieHome>>>
    fun getMovieList(params: Params.MovieParams): RepoResult<Movie>
    fun getMovieDetail(needFetch: Boolean, movieId: Int): Flow<Resource<MovieDetail>>
    fun getAllFavoriteMovie(): Flow<PagedList<Movie>>
    fun getSearchMovie(query: String): Flow<Resource<List<Movie>>>

    fun getTvHome(): Flow<List<Resource<TvHome>>>
    fun getTvShowsList(params: Params.MovieParams): RepoResult<Tv>
    fun getTvShowDetail(needFetch: Boolean, tvShowId: Int): Flow<Resource<TvDetail>>
    fun getAllFavoriteTv(): Flow<PagedList<Tv>>
    fun getSearchTv(query: String): Flow<Resource<List<Tv>>>

    fun setFavoriteMovie(id: Int)
    fun getFavoriteMovieById(id: Int): Flow<List<FavoriteMovieEntity>>
    fun deleteFavoriteMovie(id: Int)

    fun setFavoriteTv(id: Int)
    fun getFavoriteTvById(id: Int): Flow<List<FavoriteTvEntity>>
    fun deleteFavoriteTv(id: Int)

    fun getCreditsMovie(id: Int): Flow<Resource<Credits>>
    fun getCreditsTv(id: Int): Flow<Resource<Credits>>
    fun getVideoMovie(id: Int): Flow<Resource<List<Video>>>
    fun getVideoTv(id: Int): Flow<Resource<List<Video>>>
    fun getSimilarMovie(id: Int): Flow<Resource<List<Movie>>>
    fun getSimilarTv(id: Int): Flow<Resource<List<Tv>>>

    fun getAllGenreMovie(): Flow<Resource<List<Genre>>>
    fun getAllGenreListTv(): Flow<Resource<List<Genre>>>
}