package com.sstudio.submissionbajetpackpro.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import com.sstudio.submissionbajetpackpro.core.data.FakeMovieTvRepository
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.usecase.FakeMovieTvInteractor
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.core.utils.DataDummy
import com.sstudio.submissionbajetpackpro.core.utils.DataMapper
import com.sstudio.submissionbajetpackpro.vo.Resource
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

    private val dummyMovies = Resource.success(DataDummy.generateDummyMovies()[0])
    private val movieId = dummyMovies.data?.id as Int
    private val dummyTvShows = Resource.success(DataDummy.generateDummyTvShow()[0])
    private val tvId = dummyTvShows.data?.id as Int
    private lateinit var movieTvUseCase: MovieTvUseCase
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: FakeMovieTvRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<Movie>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<Tv>>

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
    fun testGetMovie() {
        val dummyLiveData = MutableLiveData<Resource<MovieEntity>>()
        dummyLiveData.value = dummyMovies

        Mockito.`when`(movieTvRepository.getMovieDetail(false, movieId)).thenReturn(
            Transformations.map(dummyLiveData) { resource ->
                Resource.success(resource.data?.let { DataMapper.mapMovieEntitiesToDomain(it) })
            }.asFlow()
        )
        viewModel.setSelectedMovieTv(movieId)
        viewModel.detailMovie.observeForever(movieObserver)
        Mockito.verify(movieTvRepository).getMovieDetail(false, movieId)
        Thread.sleep(2000)
        Mockito.verify(movieObserver).onChanged(
            Resource.success(DataMapper.mapMovieEntitiesToDomain(dummyMovies.data as MovieEntity))
        )
        val movieDetail = viewModel.detailMovie.value?.data
        assertNotNull(movieDetail)
    }

    @Test
    fun testGetTv() {
        val tvShow = MutableLiveData<Resource<TvEntity>>()
        tvShow.value = dummyTvShows

        Mockito.`when`(movieTvRepository.getTvShowDetail(false, tvId)).thenReturn(
            tvShow.map { resource ->
                Resource.success(resource.data?.let { DataMapper.mapTvEntitiesToDomain(it) })
            }.asFlow()
        )
        viewModel.setSelectedMovieTv(tvId)
        viewModel.detailTv.observeForever(tvObserver)
        Mockito.verify(movieTvRepository).getTvShowDetail(false, tvId)
        Thread.sleep(2000)
        Mockito.verify(tvObserver).onChanged(
            Resource.success(DataMapper.mapTvEntitiesToDomain(dummyTvShows.data as TvEntity))
        )
        val tvShowEntity = viewModel.detailTv.value?.data
        assertNotNull(tvShowEntity)
    }
}