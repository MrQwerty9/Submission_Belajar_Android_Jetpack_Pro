package com.sstudio.submissionbajetpackpro.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.verify
import com.sstudio.submissionbajetpackpro.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.utils.AppExecutors
import com.sstudio.submissionbajetpackpro.utils.DataDummy
import com.sstudio.submissionbajetpackpro.utils.LiveDataTestUtil
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieTvRepositoryTest : TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val movieTvRepository = FakeMovieTvRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.generateRemoteDummyMovies()
    private val detailMovieResponses = DataDummy.generateRemoteDummyMovies().results[0]
    private val movieId = movieResponses.results[0].id
    private val tvShowResponses = DataDummy.generateRemoteDummyTvShow()
    private val detailTvShowResponses = DataDummy.generateRemoteDummyTvShow().results[0]
    private val tvId = tvShowResponses.results[0].id

    @Test
    fun testGetAllMovie() {
        val dummyMovie = MutableLiveData<List<MovieEntity>>()
        dummyMovie.value = DataDummy.generateDummyMovies()
        `when`(local.getAllMovie()).thenReturn(dummyMovie)

        val courseEntities = LiveDataTestUtil.getValue(movieTvRepository.getAllMovie())
        verify(local).getAllMovie()
        assertNotNull(courseEntities.data)
        assertEquals(movieResponses.results.size.toLong(), courseEntities.data?.size?.toLong())
    }

    @Test
    fun testGetAllTvShows() {
        val dummyTvShows = MutableLiveData<List<TvEntity>>()
        dummyTvShows.value = DataDummy.generateDummyTvShow()
        `when`(local.getAllTv()).thenReturn(dummyTvShows)

        val tvShowEntities = LiveDataTestUtil.getValue(movieTvRepository.getAllTvShows())
        verify(local).getAllTv()
        assertNotNull(tvShowEntities.data)
        assertEquals(tvShowResponses.results.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun testGetMovieDetail() {
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = DataDummy.generateDummyMovies()[0]
        `when`(local.getMovieById(movieId)).thenReturn(dummyMovie)

        val movieEntities = LiveDataTestUtil.getValue(movieTvRepository.getMovieDetail(movieId))
        verify(local).getMovieById(movieId)
        Assert.assertNotNull(movieEntities)
        assertEquals(detailMovieResponses.originalTitle, movieEntities.data?.originalTitle)
    }

    @Test
    fun testGetTvShowDetail() {
        val dummyTv = MutableLiveData<TvEntity>()
        dummyTv.value = DataDummy.generateDummyTvShow()[0]
        `when`(local.getTvById(tvId)).thenReturn(dummyTv)

        val tvShowEntities = LiveDataTestUtil.getValue(movieTvRepository.getTvShowDetail(tvId))
        verify(local).getTvById(tvId)
        Assert.assertNotNull(tvShowEntities)
        assertEquals(detailTvShowResponses.originalName, tvShowEntities.data?.originalName)
    }
}