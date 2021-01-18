package com.sstudio.submissionbajetpackpro.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.FakeMovieTvRepository
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.vo.Resource

class FakeMovieTvInteractor(private val fakeMovieTvRepository: FakeMovieTvRepository): MovieTvUseCase {
    override fun getAllMovie(needFetch: Boolean): LiveData<Resource<PagedList<Movie>>> =
        fakeMovieTvRepository.getAllMovie(needFetch)

    override fun getMovieDetail(needFetch: Boolean, movieId: Int): LiveData<Resource<Movie>> =
        fakeMovieTvRepository.getMovieDetail(needFetch, movieId)

    override fun getAllFavoriteMovie(): LiveData<PagedList<Movie>> =
        fakeMovieTvRepository.getAllFavoriteMovie()

    override fun getAllTvShows(needFetch: Boolean): LiveData<Resource<PagedList<Tv>>> =
        fakeMovieTvRepository.getAllTvShows(needFetch)

    override fun getTvShowDetail(needFetch: Boolean, tvShowId: Int): LiveData<Resource<Tv>> =
        fakeMovieTvRepository.getTvShowDetail(needFetch, tvShowId)

    override fun getAllFavoriteTv(): LiveData<PagedList<Tv>> =
        fakeMovieTvRepository.getAllFavoriteTv()

    override fun setFavorite(id: Int) =
        fakeMovieTvRepository.setFavorite(id)

    override fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>> =
        fakeMovieTvRepository.getFavoriteById(id)

    override fun deleteFavorite(id: Int) =
        fakeMovieTvRepository.deleteFavorite(id)
}