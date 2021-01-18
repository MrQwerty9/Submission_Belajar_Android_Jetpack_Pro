package com.sstudio.submissionbajetpackpro.ui.favorite.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.FakeMovieTvRepository
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
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
class FavoriteTvShowViewModelTest {

    private lateinit var viewModel: FavoriteTvShowViewModel
    private lateinit var movieTvUseCase: MovieTvUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: FakeMovieTvRepository

    @Mock
    private lateinit var observer: Observer<PagedList<Tv>>

    @Mock
    private lateinit var pagedList: PagedList<Tv>

    @Before
    fun setUp() {
        movieTvUseCase = FakeMovieTvInteractor(movieTvRepository)
        viewModel = FavoriteTvShowViewModel(movieTvUseCase)
    }

    @Test
    fun testGetMovies() {
        val dataTvShows = pagedList
        `when`(dataTvShows.size).thenReturn(2)
        val tv = MutableLiveData<PagedList<Tv>>()
        tv.value = dataTvShows

        `when`(movieTvRepository.getAllFavoriteTv()).thenReturn(tv)
        val movieEntities = viewModel.listTv?.value
        Mockito.verify(movieTvRepository).getAllFavoriteTv()
        Assert.assertNotNull(movieEntities)
        Assert.assertEquals(2, movieEntities?.size)

        viewModel.listTv?.observeForever(observer)
        Mockito.verify(observer).onChanged(dataTvShows)

    }
}