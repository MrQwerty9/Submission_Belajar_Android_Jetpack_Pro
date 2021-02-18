package com.sstudio.submissionbajetpackpro.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.domain.model.MovieDetail
import com.sstudio.submissionbajetpackpro.core.domain.model.TvDetail
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.core.utils.DataDummy
import com.sstudio.submissionbajetpackpro.ui.coreTest.data.FakeMovieTvRepository
import com.sstudio.submissionbajetpackpro.ui.coreTest.usecase.FakeMovieTvInteractor
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel

    private val dummyMovies = Resource.Success(DataDummy.generateDummyMovieDetail())
    private val movieId = dummyMovies.data?.id as Int
    private val dummyTvShows = Resource.Success(DataDummy.generateDummyTvDetail())
    private val tvId = dummyTvShows.data?.id as Int
    private lateinit var movieTvUseCase: MovieTvUseCase
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: FakeMovieTvRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieDetail>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<TvDetail>>

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        movieTvUseCase = FakeMovieTvInteractor(movieTvRepository)
        viewModel = DetailViewModel(movieTvUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testGetDetailMovie() {
        val dummyLiveData = MutableLiveData<Resource<MovieDetail>>()
        dummyLiveData.value = dummyMovies

        Mockito.`when`(movieTvRepository.getMovieDetail(movieId)).thenReturn(dummyLiveData.asFlow())
        viewModel.setSelectedMovieTv(movieId, DetailData.Type.MOVIE)
        viewModel.detailMovie?.observeForever(movieObserver)
        Mockito.verify(movieTvRepository).getMovieDetail(movieId)
        Thread.sleep(2000)
        Mockito.verify(movieObserver).onChanged(Resource.Success(dummyMovies.data as MovieDetail))
        val movieDetail = viewModel.detailMovie?.value?.data
        assertNotNull(movieDetail)
    }

    @Test
    fun testGetDetailTv() {
        val tvShow = MutableLiveData<Resource<TvDetail>>()
        tvShow.value = dummyTvShows

        Mockito.`when`(movieTvRepository.getTvShowDetail(tvId)).thenReturn(tvShow.asFlow())
        viewModel.setSelectedMovieTv(tvId, DetailData.Type.TV_SHOW)
        viewModel.detailTv?.observeForever(tvObserver)
        Mockito.verify(movieTvRepository).getTvShowDetail(tvId)
        Thread.sleep(2000)
        Mockito.verify(tvObserver).onChanged(Resource.Success(dummyTvShows.data as TvDetail))
        val tvShowEntity = viewModel.detailTv?.value?.data
        assertNotNull(tvShowEntity)
    }

    fun testGetFavoriteStatus() {

    }

    fun testUpdateFavorite() {}

    fun testGetGetCredits() {}

    fun testFetchCredits() {}

    fun testGetGetSimilarMovie() {}

    fun testGetGetSimilarTv() {}

    fun testGetGetVideo() {}
}