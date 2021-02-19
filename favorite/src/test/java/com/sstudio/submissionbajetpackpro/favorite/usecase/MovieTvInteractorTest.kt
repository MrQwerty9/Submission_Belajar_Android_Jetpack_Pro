package com.sstudio.submissionbajetpackpro.favorite.usecase

import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteMovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteTvEntity
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RepoResult
import com.sstudio.submissionbajetpackpro.core.domain.model.*
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.core.utils.Params
import com.sstudio.submissionbajetpackpro.favorite.data.FakeMovieTvRepository
import kotlinx.coroutines.flow.Flow

class FakeMovieTvInteractor(private val fakeMovieTvRepository: FakeMovieTvRepository): MovieTvUseCase {

        override fun getMovieHome(): Flow<List<Resource<MovieHome>>> =
            fakeMovieTvRepository.getMovieHome()

        override fun getMovieList(params: Params.MovieParams): RepoResult<Movie> =
            fakeMovieTvRepository.getMovieList(params)

        override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> =
            fakeMovieTvRepository.getMovieDetail(movieId)

        override fun getAllFavoriteMovie(): Flow<PagedList<Movie>> =
            fakeMovieTvRepository.getAllFavoriteMovie()

        override fun getSearchMovie(query: String): Flow<Resource<List<Movie>>> =
            fakeMovieTvRepository.getSearchMovie(query)

        override fun getTvHome(): Flow<List<Resource<TvHome>>> =
            fakeMovieTvRepository.getTvHome()

        override fun getTvShowsList(params: Params.MovieParams): RepoResult<Tv> =
            fakeMovieTvRepository.getTvShowsList(params)

        override fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvDetail>> =
            fakeMovieTvRepository.getTvShowDetail(tvShowId)

        override fun getAllFavoriteTv(): Flow<PagedList<Tv>> =
            fakeMovieTvRepository.getAllFavoriteTv()

        override fun getSearchTv(query: String): Flow<Resource<List<Tv>>> =
            fakeMovieTvRepository.getSearchTv(query)

        override fun setFavoriteMovie(id: Int) {
            fakeMovieTvRepository.setFavoriteMovie(id)
        }

        override fun getFavoriteMovieById(id: Int): Flow<List<FavoriteMovieEntity>> =
            fakeMovieTvRepository.getFavoriteMovieById(id)

        override fun deleteFavoriteMovie(id: Int) {
            fakeMovieTvRepository.deleteFavoriteMovie(id)
        }

        override fun setFavoriteTv(id: Int) {
            fakeMovieTvRepository.setFavoriteTv(id)
        }

        override fun getFavoriteTvById(id: Int): Flow<List<FavoriteTvEntity>> =
            fakeMovieTvRepository.getFavoriteTvById(id)

        override fun deleteFavoriteTv(id: Int) {
            fakeMovieTvRepository.deleteFavoriteTv(id)
        }

        override fun getCreditsMovie(id: Int): Flow<Resource<Credits>> =
            fakeMovieTvRepository.getCreditsMovie(id)

        override fun getCreditsTv(id: Int): Flow<Resource<Credits>> =
            fakeMovieTvRepository.getCreditsTv(id)

        override fun getVideoMovie(id: Int): Flow<Resource<List<Video>>> =
            fakeMovieTvRepository.getVideoMovie(id)

        override fun getVideoTv(id: Int): Flow<Resource<List<Video>>> =
            fakeMovieTvRepository.getVideoTv(id)

        override fun getSimilarMovie(id: Int): Flow<Resource<List<Movie>>> =
            fakeMovieTvRepository.getSimilarMovie(id)

        override fun getSimilarTv(id: Int): Flow<Resource<List<Tv>>> =
            fakeMovieTvRepository.getSimilarTv(id)

        override fun getAllGenreMovie(): Flow<Resource<List<Genre>>> =
            fakeMovieTvRepository.getAllGenreMovie()

        override fun getAllGenreTv(): Flow<Resource<List<Genre>>> =
            fakeMovieTvRepository.getAllGenreTv()
    }