package com.sstudio.submissionbajetpackpro.core.domain.usecase

import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RepoResult
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.MovieHome
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.model.TvHome
import com.sstudio.submissionbajetpackpro.core.utils.Params
import kotlinx.coroutines.flow.Flow

class MovieTvInteractor(private val movieTvRepository: MovieTvRepository): MovieTvUseCase {

    override fun getMovieHome(): Flow<List<Resource<MovieHome>>> =
        movieTvRepository.getMovieHome()

    override fun getMovieList(params: Params.MovieParams): RepoResult<Movie> =
        movieTvRepository.getMovieList(params)

    override fun getMovieDetail(needFetch: Boolean, movieId: Int): Flow<Resource<Movie>> =
        movieTvRepository.getMovieDetail(needFetch, movieId)

    override fun getAllFavoriteMovie(): Flow<PagedList<Movie>> =
        movieTvRepository.getAllFavoriteMovie()

    override fun getSearchMovie(query: String): Flow<Resource<List<Movie>>> =
        movieTvRepository.getSearchMovie(query)

    override fun getTvHome(): Flow<List<Resource<TvHome>>> =
        movieTvRepository.getTvHome()

    override fun getTvShowsList(params: Params.MovieParams): RepoResult<Tv> =
        movieTvRepository.getTvShowsList(params)

    override fun getTvShowDetail(needFetch: Boolean, tvShowId: Int): Flow<Resource<Tv>> =
        movieTvRepository.getTvShowDetail(needFetch, tvShowId)

    override fun getAllFavoriteTv(): Flow<PagedList<Tv>> =
        movieTvRepository.getAllFavoriteTv()

    override fun getSearchTv(query: String): Flow<Resource<List<Tv>>> =
        movieTvRepository.getSearchTv(query)

    override fun setFavorite(id: Int) =
        movieTvRepository.setFavorite(id)

    override fun getFavoriteById(id: Int): Flow<List<FavoriteEntity>> =
        movieTvRepository.getFavoriteById(id)

    override fun deleteFavorite(id: Int) =
        movieTvRepository.deleteFavorite(id)
}