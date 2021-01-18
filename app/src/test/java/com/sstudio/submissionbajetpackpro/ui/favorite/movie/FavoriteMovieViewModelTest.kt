package com.sstudio.submissionbajetpackpro.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.FakeMovieTvRepository
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.usecase.FakeMovieTvInteractor
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteMovieViewModelTest {

    private lateinit var viewModel: FavoriteMovieViewModel
    private lateinit var movieTvUseCase: MovieTvUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: FakeMovieTvRepository

    @Mock
    private lateinit var observer: Observer<PagedList<Movie>>

    @Mock
    private lateinit var pagedList: PagedList<Movie>

    @Before
    fun setUp() {
        movieTvUseCase = FakeMovieTvInteractor(movieTvRepository)
        viewModel = FavoriteMovieViewModel(movieTvUseCase)
    }

    @Test
    fun testGetMovies() {
        val dataMovies = pagedList
        `when`(dataMovies.size).thenReturn(2)
        val movies = MutableLiveData<PagedList<Movie>>()
        movies.value = dataMovies

        `when`(movieTvRepository.getAllFavoriteMovie()).thenReturn(movies)
        val movieEntities = viewModel.listMovie?.value
        Mockito.verify(movieTvRepository).getAllFavoriteMovie()
        Assert.assertNotNull(movieEntities)
        Assert.assertEquals(2, movieEntities?.size)

        viewModel.listMovie?.observeForever(observer)
        Mockito.verify(observer).onChanged(dataMovies)

    }
}