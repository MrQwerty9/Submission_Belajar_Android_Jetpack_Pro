package com.sstudio.submissionbajetpackpro.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import com.sstudio.submissionbajetpackpro.core.data.FakeMovieTvRepository
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.core.usecase.FakeMovieTvInteractor
import com.sstudio.submissionbajetpackpro.core.utils.DataDummy
import com.sstudio.submissionbajetpackpro.core.utils.DataMapper
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

    private val dummyMovies = Resource.Success(DataDummy.generateDummyMovies()[0])
    private val movieId = dummyMovies.data?.id as Int
    private val dummyTvShows = Resource.Success(DataDummy.generateDummyTvShow()[0])
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
                Resource.Success(DataMapper.mapMovieEntitiesToDomain(resource.data as MovieEntity))
            }.asFlow()
        )
        viewModel.setSelectedMovieTv(movieId)
        viewModel.detailMovie.observeForever(movieObserver)
        Mockito.verify(movieTvRepository).getMovieDetail(false, movieId)
        Thread.sleep(2000)
        Mockito.verify(movieObserver).onChanged(
            Resource.Success(DataMapper.mapMovieEntitiesToDomain(dummyMovies.data as MovieEntity))
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
                Resource.Success(DataMapper.mapTvEntitiesToDomain(resource.data as TvEntity))
            }.asFlow()
        )
        viewModel.setSelectedMovieTv(tvId)
        viewModel.detailTv.observeForever(tvObserver)
        Mockito.verify(movieTvRepository).getTvShowDetail(false, tvId)
        Thread.sleep(2000)
        Mockito.verify(tvObserver).onChanged(
            Resource.Success(DataMapper.mapTvEntitiesToDomain(dummyTvShows.data as TvEntity))
        )
        val tvShowEntity = viewModel.detailTv.value?.data
        assertNotNull(tvShowEntity)
    }
}