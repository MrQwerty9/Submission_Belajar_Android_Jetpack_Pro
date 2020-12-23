package com.sstudio.submissionbajetpackpro.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.utils.DataDummy
import com.sstudio.submissionbajetpackpro.vo.Resource
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

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<TvEntity>>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(movieTvRepository)
    }

    @Test
    fun testGetMovie() {
        viewModel.setSelectedMovieTv(movieId)
        val dummyData = dummyMovies.data as MovieEntity
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyMovies

        Mockito.`when`(movieTvRepository.getMovieDetail(movieId)).thenReturn(movie)
//        val movieEntity = viewModel.detailMovie.value?.data
//        Mockito.verify(movieTvRepository).getMovieDetail(movieId)

//        assertNotNull(movieEntity)
//        assertEquals(dummyData.backdropPath, movieEntity?.backdropPath)
//        assertEquals(dummyData.genreIds, movieEntity?.genreIds)
//        assertEquals(dummyData.id, movieEntity?.id)
//        assertEquals(dummyData.originalTitle, movieEntity?.originalTitle)
//        assertEquals(dummyData.overview, movieEntity?.overview)
//        assertEquals(dummyData.posterPath, movieEntity?.posterPath)
//        assertEquals(dummyData.releaseDate, movieEntity?.releaseDate)
//        assertEquals(dummyData.voteAverage, movieEntity?.voteAverage)

        viewModel.detailMovie.observeForever(movieObserver)
        Mockito.verify(movieObserver).onChanged(dummyMovies)
    }

    @Test
    fun testGetTv() {
        viewModel.setSelectedMovieTv(tvId)
        val dummyData = dummyTvShows.data as TvEntity
        val tvShow = MutableLiveData<Resource<TvEntity>>()
        tvShow.value = dummyTvShows

        Mockito.`when`(movieTvRepository.getTvShowDetail(tvId)).thenReturn(tvShow)
//        val tvShowEntity = viewModel.detailTv.value?.data
//        Mockito.verify(movieTvRepository).getTvShowDetail(tvId)
//        Assert.assertNotNull(tvShowEntity)
//        assertEquals(dummyData.backdropPath, tvShowEntity?.backdropPath)
//        assertEquals(dummyData.genreIds, tvShowEntity?.genreIds)
//        assertEquals(dummyData.id, tvShowEntity?.id)
//        assertEquals(dummyData.originalName, tvShowEntity?.originalName)
//        assertEquals(dummyData.overview, tvShowEntity?.overview)
//        assertEquals(dummyData.posterPath, tvShowEntity?.posterPath)
//        assertEquals(dummyData.firstAirDate, tvShowEntity?.firstAirDate)
//        assertEquals(dummyData.voteAverage, tvShowEntity?.voteAverage)

        viewModel.detailTv.observeForever(tvObserver)
        Mockito.verify(tvObserver).onChanged(dummyTvShows)
    }
}