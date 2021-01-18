package com.sstudio.submissionbajetpackpro.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.FakeMovieTvRepository
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.usecase.FakeMovieTvInteractor
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.vo.Resource
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
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel
    private lateinit var movieTvUseCase: MovieTvUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: FakeMovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<Movie>>>

    @Mock
    private lateinit var pagedList: PagedList<Movie>

    @Before
    fun setUp() {
        movieTvUseCase = FakeMovieTvInteractor(movieTvRepository)
        viewModel = MovieViewModel(movieTvUseCase)
    }

    @Test
    fun testGetMovies() {
        val dataMovies = Resource.success(pagedList)
        `when`(dataMovies.data?.size).thenReturn(5)
        val movies = MutableLiveData<Resource<PagedList<Movie>>>()
        movies.value = dataMovies

        `when`(movieTvRepository.getAllMovie(false)).thenReturn(movies)
        val movieEntities = viewModel.listMovie?.value?.data
        Mockito.verify(movieTvRepository).getAllMovie(false)
        Assert.assertNotNull(movieEntities)
        Assert.assertEquals(5, movieEntities?.size)

        viewModel.listMovie?.observeForever(observer)
        Mockito.verify(observer).onChanged(dataMovies)

    }
}