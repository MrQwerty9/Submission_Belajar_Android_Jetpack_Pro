package com.sstudio.submissionbajetpackpro.core.domain.usecase

import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteMovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteTvEntity
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RepoResult
import com.sstudio.submissionbajetpackpro.core.domain.model.*
import com.sstudio.submissionbajetpackpro.core.utils.Params
import kotlinx.coroutines.flow.Flow

class MovieTvInteractor(private val movieTvRepository: MovieTvRepository): MovieTvUseCase {

    override fun getMovieHome(): Flow<List<Resource<MovieHome>>> =
        movieTvRepository.getMovieHome()

    override fun getMovieList(params: Params.MovieParams): RepoResult<Movie> =
        movieTvRepository.getMovieList(params)

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> =
        movieTvRepository.getMovieDetail(movieId)

    override fun getAllFavoriteMovie(): Flow<PagedList<Movie>> =
        movieTvRepository.getAllFavoriteMovie()

    override fun getSearchMovie(query: String): Flow<Resource<List<Movie>>> =
        movieTvRepository.getSearchMovie(query)

    override fun getTvHome(): Flow<List<Resource<TvHome>>> =
        movieTvRepository.getTvHome()

    override fun getTvShowsList(params: Params.MovieParams): RepoResult<Tv> =
        movieTvRepository.getTvShowsList(params)

    override fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvDetail>> =
        movieTvRepository.getTvShowDetail(tvShowId)

    override fun getAllFavoriteTv(): Flow<PagedList<Tv>> =
        movieTvRepository.getAllFavoriteTv()

    override fun getSearchTv(query: String): Flow<Resource<List<Tv>>> =
        movieTvRepository.getSearchTv(query)

    override fun setFavoriteMovie(id: Int) {
        movieTvRepository.setFavoriteMovie(id)
    }

    override fun getFavoriteMovieById(id: Int): Flow<List<FavoriteMovieEntity>> =
        movieTvRepository.getFavoriteMovieById(id)

    override fun deleteFavoriteMovie(id: Int) {
        movieTvRepository.deleteFavoriteMovie(id)
    }

    override fun setFavoriteTv(id: Int) {
        movieTvRepository.setFavoriteTv(id)
    }

    override fun getFavoriteTvById(id: Int): Flow<List<FavoriteTvEntity>> =
        movieTvRepository.getFavoriteTvById(id)

    override fun deleteFavoriteTv(id: Int) {
        movieTvRepository.deleteFavoriteTv(id)
    }

    override fun getCreditsMovie(id: Int): Flow<Resource<Credits>> =
        movieTvRepository.getCreditsMovie(id)

    override fun getCreditsTv(id: Int): Flow<Resource<Credits>> =
        movieTvRepository.getCreditsTv(id)

    override fun getVideoMovie(id: Int): Flow<Resource<List<Video>>> =
        movieTvRepository.getVideoMovie(id)

    override fun getVideoTv(id: Int): Flow<Resource<List<Video>>> =
        movieTvRepository.getVideoTv(id)

    override fun getSimilarMovie(id: Int): Flow<Resource<List<Movie>>> =
        movieTvRepository.getSimilarMovie(id)

    override fun getSimilarTv(id: Int): Flow<Resource<List<Tv>>> =
        movieTvRepository.getSimilarTv(id)

    override fun getAllGenreMovie(): Flow<Resource<List<Genre>>> =
        movieTvRepository.getAllGenreMovie()

    override fun getAllGenreListTv(): Flow<Resource<List<Genre>>> =
        movieTvRepository.getAllGenreTv()
}