package com.sstudio.submissionbajetpackpro.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.vo.NetworkState
import kotlinx.coroutines.flow.Flow

class MovieTvInteractor(private val movieTvRepository: MovieTvRepository): MovieTvUseCase {
    override fun getAllMovie(needFetch: Boolean): Flow<PagedList<Movie>> =
        movieTvRepository.getAllMovie(needFetch)

    override fun getAllMovieState(): LiveData<NetworkState> =
        movieTvRepository.getAllMovieState()

    override fun getMovieDetail(needFetch: Boolean, movieId: Int): Flow<Resource<Movie>> =
        movieTvRepository.getMovieDetail(needFetch, movieId)

    override fun getAllFavoriteMovie(): Flow<PagedList<Movie>> =
        movieTvRepository.getAllFavoriteMovie()

    override fun getSearchMovie(query: String): Flow<Resource<List<Movie>>> =
        movieTvRepository.getSearchMovie(query)

    override fun getAllTvShows(needFetch: Boolean): Flow<Resource<PagedList<Tv>>> =
        movieTvRepository.getAllTvShows(needFetch)

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