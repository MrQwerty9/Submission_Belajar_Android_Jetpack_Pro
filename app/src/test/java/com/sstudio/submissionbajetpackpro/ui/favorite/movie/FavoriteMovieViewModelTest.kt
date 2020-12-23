package com.sstudio.submissionbajetpackpro.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieFavorite
import com.sstudio.submissionbajetpackpro.utils.DataDummy
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteMovieViewModelTest {

    private lateinit var viewModel: FavoriteMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<List<MovieFavorite>>

    @Before
    fun setUp() {
        viewModel = FavoriteMovieViewModel(movieTvRepository)
    }

    @Test
    fun testGetMovies() {
        val dataMovies = DataDummy.generateDummyFavMovie()
        val movies = MutableLiveData<List<MovieFavorite>>()
        movies.value = dataMovies

        Mockito.`when`(movieTvRepository.getAllFavoriteMovie()).thenReturn(movies)
        val movieEntities = viewModel.listMovie?.value
        Mockito.verify(movieTvRepository).getAllFavoriteMovie()
        Assert.assertNotNull(movieEntities)
        Assert.assertEquals(2, movieEntities?.size)

        viewModel.listMovie?.observeForever(observer)
        Mockito.verify(observer).onChanged(dataMovies)

    }
}