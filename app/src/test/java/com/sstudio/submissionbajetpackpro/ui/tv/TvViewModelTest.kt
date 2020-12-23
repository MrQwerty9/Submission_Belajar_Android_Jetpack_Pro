package com.sstudio.submissionbajetpackpro.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.utils.DataDummy
import com.sstudio.submissionbajetpackpro.vo.Resource
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvViewModelTest {

    private lateinit var viewModel: TvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resource<List<TvEntity>>>

    @Before
    fun setUp() {
        viewModel = TvViewModel(movieTvRepository)
    }

    @Test
    fun testGetTvShow() {
        val dataTvShows = Resource.success(DataDummy.generateDummyTvShow())
        val tvShows = MutableLiveData<Resource<List<TvEntity>>>()
        tvShows.value = dataTvShows

        Mockito.`when`(movieTvRepository.getAllTvShows()).thenReturn(tvShows)
        val tvShowEntities = viewModel.listTvShow?.value?.data
        Mockito.verify(movieTvRepository).getAllTvShows()
        Assert.assertNotNull(tvShowEntities)
        Assert.assertEquals(5, tvShowEntities?.size)

        viewModel.listTvShow?.observeForever(observer)
        Mockito.verify(observer).onChanged(dataTvShows)
    }
}