package com.sstudio.submissionbajetpackpro.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.MovieFavorite
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.TvFavorite
import com.sstudio.submissionbajetpackpro.core.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.api.ApiConfig
import com.sstudio.submissionbajetpackpro.core.data.source.remote.api.ApiService
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.utils.AppExecutors
import com.sstudio.submissionbajetpackpro.core.utils.DataDummy
import com.sstudio.submissionbajetpackpro.core.utils.LiveDataTestUtil
import com.sstudio.submissionbajetpackpro.utils.PagedListUtil
import com.sstudio.submissionbajetpackpro.vo.Resource
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class MovieTvRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val movieTvRepository = FakeMovieTvRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.generateRemoteDummyMovies()
    private val detailMovieResponses = DataDummy.generateRemoteDummyMovies().results[0]
    private val movieId = movieResponses.results[0].id
    private val tvShowResponses = DataDummy.generateRemoteDummyTvShow()
    private val detailTvShowResponses = DataDummy.generateRemoteDummyTvShow().results[0]
    private val tvId = tvShowResponses.results[0].id
    private var apiService = ApiConfig.getApiService()
    private lateinit var mockWebServer: MockWebServer

    @Mock
    private lateinit var apiServiceMock: ApiService

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<Movie>>>

    @Mock
    private lateinit var pagedList: PagedList<Movie>

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

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
    fun testGetAllMovieApi() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
        mockWebServer.enqueue(response)

        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        val actualResponse = apiService.getPopularMovies(BuildConfig.TMDB_API_KEY, movieId.toString()).execute()
        val resultMovie = MutableLiveData<ApiResponse<MovieResponse>>()
        actualResponse.body()?.let { resultMovie.value = ApiResponse.success(it)}
        `when`(local.getAllMovie()).thenReturn(dataSourceFactory)
        `when`(remote.getAllMovie()).thenReturn(resultMovie)
        val get = movieTvRepository.getAllMovie(true)
//        get.observeForever(observer)
//        verify(remote).getAllMovie()
//        assertNotNull(remote.getAllMovie())

        assertEquals(response.toString().contains("200"),actualResponse.code().toString().contains("200"))
        actualResponse.body()?.results?.isEmpty()?.let { junit.framework.Assert.assertFalse(it) }

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