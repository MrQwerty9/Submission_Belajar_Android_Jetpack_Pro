package com.sstudio.submissionbajetpackpro.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.vo.Resource

class MovieTvInteractor(private val movieTvRepository: MovieTvRepository): MovieTvUseCase {
    override fun getAllMovie(needFetch: Boolean): LiveData<Resource<PagedList<Movie>>> =
        movieTvRepository.getAllMovie(needFetch)

    override fun getMovieDetail(needFetch: Boolean, movieId: Int): LiveData<Resource<Movie>> =
        movieTvRepository.getMovieDetail(needFetch, movieId)

    override fun getAllFavoriteMovie(): LiveData<PagedList<Movie>> =
        movieTvRepository.getAllFavoriteMovie()

    override fun getAllTvShows(needFetch: Boolean): LiveData<Resource<PagedList<Tv>>> =
        movieTvRepository.getAllTvShows(needFetch)

    override fun getTvShowDetail(needFetch: Boolean, tvShowId: Int): LiveData<Resource<Tv>> =
        movieTvRepository.getTvShowDetail(needFetch, tvShowId)

    override fun getAllFavoriteTv(): LiveData<PagedList<Tv>> =
        movieTvRepository.getAllFavoriteTv()

    override fun setFavorite(id: Int) =
        movieTvRepository.setFavorite(id)

    override fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>> =
        movieTvRepository.getFavoriteById(id)

    override fun deleteFavorite(id: Int) =
        movieTvRepository.deleteFavorite(id)
}