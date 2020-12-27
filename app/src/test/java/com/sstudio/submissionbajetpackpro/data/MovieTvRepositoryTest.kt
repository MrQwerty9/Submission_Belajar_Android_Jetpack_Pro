package com.sstudio.submissionbajetpackpro.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.verify
import com.sstudio.submissionbajetpackpro.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.MovieFavorite
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.data.source.local.entity.TvFavorite
import com.sstudio.submissionbajetpackpro.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.utils.AppExecutors
import com.sstudio.submissionbajetpackpro.utils.DataDummy
import com.sstudio.submissionbajetpackpro.utils.LiveDataTestUtil
import com.sstudio.submissionbajetpackpro.utils.PagedListUtil
import com.sstudio.submissionbajetpackpro.vo.Resource
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
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
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovie()).thenReturn(dataSourceFactory)
        movieTvRepository.getAllMovie(false)

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllMovie()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.results.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun testGetMovieDetail() {
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = DataDummy.generateDummyMovies()[0]
        `when`(local.getMovieById(movieId)).thenReturn(dummyMovie)

        val movieEntities = LiveDataTestUtil.getValue(movieTvRepository.getMovieDetail(false, movieId))
        verify(local).getMovieById(movieId)
        Assert.assertNotNull(movieEntities)
        assertEquals(detailMovieResponses.originalTitle, movieEntities.data?.originalTitle)
    }

    @Test
    fun testGetAllFavoriteMovie(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieFavorite>
        `when`(local.getAllFavoriteMovie()).thenReturn(dataSourceFactory)
        movieTvRepository.getAllFavoriteMovie()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllFavoriteMovie()
        assertNotNull(movieEntities)
        assertEquals(movieResponses.results.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun testGetAllTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(local.getAllTv()).thenReturn(dataSourceFactory)
        movieTvRepository.getAllTvShows(false)

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getAllTv()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponses.results.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun testGetTvShowDetail() {
        val dummyTv = MutableLiveData<TvEntity>()
        dummyTv.value = DataDummy.generateDummyTvShow()[0]
        `when`(local.getTvById(tvId)).thenReturn(dummyTv)

        val tvShowEntities = LiveDataTestUtil.getValue(movieTvRepository.getTvShowDetail(false, tvId))
        verify(local).getTvById(tvId)
        Assert.assertNotNull(tvShowEntities)
        assertEquals(detailTvShowResponses.originalName, tvShowEntities.data?.originalName)
    }

    @Test
    fun testGetAllFavoriteTvShow(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvFavorite>
        `when`(local.getAllFavoriteTv()).thenReturn(dataSourceFactory)
        movieTvRepository.getAllFavoriteTv()

        val favoriteEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getAllFavoriteTv()
        assertNotNull(favoriteEntities)
        assertEquals(movieResponses.results.size.toLong(), favoriteEntities.data?.size?.toLong())
    }
}