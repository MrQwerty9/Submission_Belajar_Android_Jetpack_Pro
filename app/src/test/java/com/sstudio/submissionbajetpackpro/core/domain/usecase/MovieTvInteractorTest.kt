package com.sstudio.submissionbajetpackpro.core.domain.usecase

import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.FakeMovieTvRepository
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import kotlinx.coroutines.flow.Flow

class FakeMovieTvInteractor(private val fakeMovieTvRepository: FakeMovieTvRepository): MovieTvUseCase {
    override fun getAllMovie(needFetch: Boolean): Flow<Resource<PagedList<Movie>>> =
        fakeMovieTvRepository.getAllMovie(needFetch)

    override fun getMovieDetail(needFetch: Boolean, movieId: Int): Flow<Resource<Movie>> =
        fakeMovieTvRepository.getMovieDetail(needFetch, movieId)

    override fun getAllFavoriteMovie(): Flow<PagedList<Movie>> =
        fakeMovieTvRepository.getAllFavoriteMovie()

    override fun getAllTvShows(needFetch: Boolean): Flow<Resource<PagedList<Tv>>> =
        fakeMovieTvRepository.getAllTvShows(needFetch)

    override fun getTvShowDetail(needFetch: Boolean, tvShowId: Int): Flow<Resource<Tv>> =
        fakeMovieTvRepository.getTvShowDetail(needFetch, tvShowId)

    override fun getAllFavoriteTv(): Flow<PagedList<Tv>> =
        fakeMovieTvRepository.getAllFavoriteTv()

    override fun setFavorite(id: Int) =
        fakeMovieTvRepository.setFavorite(id)

    override fun getFavoriteById(id: Int): Flow<List<FavoriteEntity>> =
        fakeMovieTvRepository.getFavoriteById(id)

    override fun deleteFavorite(id: Int) =
        fakeMovieTvRepository.deleteFavorite(id)
}