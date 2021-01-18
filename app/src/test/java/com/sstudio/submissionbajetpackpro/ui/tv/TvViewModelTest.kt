package com.sstudio.submissionbajetpackpro.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.FakeMovieTvRepository
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
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
class TvViewModelTest {

    private lateinit var viewModel: TvViewModel
    private lateinit var movieTvUseCase: MovieTvUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: FakeMovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<Tv>>>

    @Mock
    private lateinit var pagedList: PagedList<Tv>

    @Before
    fun setUp() {
        movieTvUseCase = FakeMovieTvInteractor(movieTvRepository)
        viewModel = TvViewModel(movieTvUseCase)
    }

    @Test
    fun testGetTvShow() {
        val dataTvShows = Resource.success(pagedList)
        `when`(dataTvShows.data?.size).thenReturn(5)
        val tvShows = MutableLiveData<Resource<PagedList<Tv>>>()
        tvShows.value = dataTvShows

        `when`(movieTvRepository.getAllTvShows(false)).thenReturn(tvShows)
        val tvShowEntities = viewModel.listTvShow?.value?.data
        Mockito.verify(movieTvRepository).getAllTvShows(false)
        Assert.assertNotNull(tvShowEntities)
        Assert.assertEquals(5, tvShowEntities?.size)

        viewModel.listTvShow?.observeForever(observer)
        Mockito.verify(observer).onChanged(dataTvShows)
    }
}