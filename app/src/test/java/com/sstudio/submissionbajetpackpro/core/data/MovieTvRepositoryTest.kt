package com.sstudio.submissionbajetpackpro.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.MovieFavorite
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.TvFavorite
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.api.ApiService
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.utils.AppExecutors
import com.sstudio.submissionbajetpackpro.core.utils.DataDummy
import com.sstudio.submissionbajetpackpro.core.utils.LiveDataTestUtil
import com.sstudio.submissionbajetpackpro.utils.PagedListUtil
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

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
    private lateinit var mockWebServer: MockWebServer
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    private lateinit var apiServiceMock: ApiService

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<Movie>>>

    @Mock
    private lateinit var pagedList: PagedList<Movie>

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
        mockWebServer.shutdown()
    }
    @Test
    fun testGetAllMovie() = runBlocking {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovie()).thenReturn(dataSourceFactory)

        movieTvRepository.getMovieList(false)

        val movieEntities = Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
//        Thread.sleep(10000)
//        verify(local).getAllMovie()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.results.size.toLong(), movieEntities.data?.size?.toLong())
    }

//    @Test
//    fun testGetAllMovieApi() {
//        val response = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//        mockWebServer.enqueue(response)
//
//        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
//        val actualResponse = apiService.getPopularMovies(BuildConfig.TMDB_API_KEY, movieId.toString()).execute()
//        val resultMovie = MutableLiveData<ApiResponse<MovieResponse>>()
//        actualResponse.body()?.let { resultMovie.value = ApiResponse.Success(it)}
//        `when`(local.getAllMovie()).thenReturn(dataSourceFactory)
//        `when`(remote.getAllMovie()).thenReturn(resultMovie)
//        val get = movieTvRepository.getAllMovie(true)
////        get.observeForever(observer)
////        verify(remote).getAllMovie()
////        assertNotNull(remote.getAllMovie())
//
//        assertEquals(response.toString().contains("200"),actualResponse.code().toString().contains("200"))
//        actualResponse.body()?.results?.isEmpty()?.let { junit.framework.Assert.assertFalse(it) }
//
//    }

    @Test
    fun testGetMovieDetail() {
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = DataDummy.generateDummyMovies()[0]
        `when`(local.getMovieById(movieId)).thenReturn(dummyMovie.asFlow())

        val movieEntities = LiveDataTestUtil.getValue(movieTvRepository.getMovieDetail(false, movieId).asLiveData())
        Thread.sleep(2000)
        verify(local).getMovieById(movieId)
        Assert.assertNotNull(movieEntities)
//        assertEquals(detailMovieResponses.originalTitle, movieEntities.data?.originalTitle)
    }

    @Test
    fun testGetAllFavoriteMovie(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieFavorite>
        `when`(local.getAllFavoriteMovie()).thenReturn(dataSourceFactory)
        movieTvRepository.getAllFavoriteMovie()

        val movieEntities = Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllFavoriteMovie()
        assertNotNull(movieEntities)
        assertEquals(movieResponses.results.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun testGetAllTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(local.getAllTv()).thenReturn(dataSourceFactory)
        movieTvRepository.getTvShowsList(false)

        val tvShowEntities = Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        Thread.sleep(2000)
        verify(local).getAllTv()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponses.results.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun testGetTvShowDetail() {
        val dummyTv = MutableLiveData<TvEntity>()
        dummyTv.value = DataDummy.generateDummyTvShow()[0]
        `when`(local.getTvById(tvId)).thenReturn(dummyTv.asFlow())

        val tvShowEntities = LiveDataTestUtil.getValue(movieTvRepository.getTvShowDetail(false, tvId).asLiveData())
        verify(local).getTvById(tvId)
        Assert.assertNotNull(tvShowEntities)
//        assertEquals(detailTvShowResponses.originalName, tvShowEntities.data?.originalName)
    }

    @Test
    fun testGetAllFavoriteTvShow(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvFavorite>
        `when`(local.getAllFavoriteTv()).thenReturn(dataSourceFactory)
        movieTvRepository.getAllFavoriteTv()

        val favoriteEntities = Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getAllFavoriteTv()
        assertNotNull(favoriteEntities)
        assertEquals(movieResponses.results.size.toLong(), favoriteEntities.data?.size?.toLong())
    }
}