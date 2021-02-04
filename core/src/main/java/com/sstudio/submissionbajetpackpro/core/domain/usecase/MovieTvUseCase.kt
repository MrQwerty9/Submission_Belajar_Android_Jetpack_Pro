package com.sstudio.submissionbajetpackpro.core.domain.usecase

import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RepoResult
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.MovieHome
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.model.TvHome
import com.sstudio.submissionbajetpackpro.core.utils.Params
import kotlinx.coroutines.flow.Flow

interface MovieTvUseCase {
    fun getMovieHome(): Flow<List<Resource<MovieHome>>>
    fun getMovieList(params: Params.MovieParams): RepoResult<Movie>
    fun getMovieDetail(needFetch: Boolean, movieId: Int): Flow<Resource<Movie>>
    fun getAllFavoriteMovie(): Flow<PagedList<Movie>>
    fun getSearchMovie(query: String): Flow<Resource<List<Movie>>>

    fun getTvHome(): Flow<List<Resource<TvHome>>>
    fun getTvShowsList(params: Params.MovieParams): RepoResult<Tv>
    fun getTvShowDetail(needFetch: Boolean, tvShowId: Int): Flow<Resource<Tv>>
    fun getAllFavoriteTv(): Flow<PagedList<Tv>>
    fun getSearchTv(query: String): Flow<Resource<List<Tv>>>

    fun setFavorite(id: Int)
    fun getFavoriteById(id: Int): Flow<List<FavoriteEntity>>
    fun deleteFavorite(id: Int)
}