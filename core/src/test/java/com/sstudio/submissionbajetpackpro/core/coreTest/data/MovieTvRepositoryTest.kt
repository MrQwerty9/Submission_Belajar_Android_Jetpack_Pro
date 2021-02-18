package com.sstudio.submissionbajetpackpro.core.coreTest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import com.sstudio.submissionbajetpackpro.core.coreTest.utils.PagedListUtil
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.MovieDetailEmbed
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.TvDetailEmbed
import com.sstudio.submissionbajetpackpro.core.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.api.ApiService
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieDetailResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.TvDetailResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.TvResponse
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.utils.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
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

    private val movieResponses = DataDummy.generateDummyMovieResponse()
    private val detailMovieResponses = DataDummy.generateDummyMovieDetailResponse()
    private val movieId = movieResponses.results[0].id
    private val tvShowResponses = DataDummy.generateDummyTvResponse()
    private val detailTvShowResponses = DataDummy.generateDummyTvResponse().results[0]
    private val tvId = tvShowResponses.results[0].id
    private lateinit var mockWebServer: MockWebServer
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val params = Params.MovieParams(listType = ListType.POPULAR)

    @Mock
    private lateinit var dataMapper: DataMapper

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


    fun testGetMovieHome() {

    }

    @Test
    fun testGetMovieList() {
        CoroutineScope(Dispatchers.IO).launch {
            val paramsPopular = Params.MovieParams(listType = ListType.POPULAR)
            val dataSourceFactoryDomain =
                mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Movie>
            val dataResponse = ApiResponse.Success(MovieResponse())
            `when`(local.getMovieListPaging(paramsPopular)).thenReturn(dataSourceFactoryDomain)
            `when`(remote.getAllMovie(paramsPopular)).thenReturn(dataResponse)

            movieTvRepository.getMovieList(paramsPopular)

            val movieEntities =
                Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyListMovieEntity()))
            verify(local).getMovieListPaging(paramsPopular)
            verify(remote).getAllMovie(paramsPopular)
            assertNotNull(movieEntities.data)
            assertEquals(movieResponses.results.size, movieEntities.data?.size)
        }
    }


    @Test
    fun testGetMovieDetail() {
        CoroutineScope(Dispatchers.IO).launch {
            val dummyMovie = MutableLiveData<MovieDetailEmbed>()
            dummyMovie.value = MovieDetailEmbed(
                movie = DataDummy.generateDummyListMovieEntity().first(),
                detailEntity = DataDummy.generateDummyDetailMovieEntity()
            )
            val dummyMovieResponse = MutableLiveData<ApiResponse<MovieDetailResponse>>()
            dummyMovieResponse.value = ApiResponse.Success(
                DataDummy.generateDummyMovieDetailResponse()
            )
            `when`(local.getMovieDetail(movieId)).thenReturn(dummyMovie.asFlow())
            `when`(remote.getMovieDetail(movieId)).thenReturn(dummyMovieResponse.asFlow())

            val movieDetail =
                LiveDataTestUtil.getValue(movieTvRepository.getMovieDetail(movieId).asLiveData())
            verify(local).getMovieDetail(movieId)
            verify(remote).getMovieDetail(movieId)
            Assert.assertNotNull(movieDetail.data)
            assertEquals(detailMovieResponses.id, movieDetail.data?.id)
            assertEquals(detailMovieResponses.title, movieDetail.data?.title)
        }
    }

    @Test
    fun testGetAllFavoriteMovie() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Movie>
        `when`(local.getAllFavoriteMovie()).thenReturn(dataSourceFactory)
        movieTvRepository.getAllFavoriteMovie()

        val movie = Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyListMovieEntity()))
        verify(local).getAllFavoriteMovie()
        assertNotNull(movie)
        assertEquals(movieResponses.results.size.toLong(), movie.data?.size?.toLong())
    }

    fun testGetSearchMovie() {}

    fun testGetTvHome() {}

    @Test
    fun testGetTvShowsList() {
        val params = Params.MovieParams(listType = ListType.POPULAR_TV_SHOW)
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Tv>
        `when`(local.getTvListPaging(params)).thenReturn(dataSourceFactory)
        movieTvRepository.getTvShowsList(params)

        val tvShow =
            Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyListTvEntity()))
        verify(local).getTvListPaging(params)
        assertNotNull(tvShow)
        assertEquals(tvShowResponses.results.size.toLong(), tvShow.data?.size?.toLong())
    }

    @Test
    fun testGetTvShowDetail() {
        CoroutineScope(Dispatchers.IO).launch {
            val dummyTv = MutableLiveData<TvDetailEmbed>()
            dummyTv.value = TvDetailEmbed(
                tv = DataDummy.generateDummyListTvEntity().first(),
                detailEntity = DataDummy.generateDummyTvDetailEntity()
            )
            val dummyTvResponse = MutableLiveData<ApiResponse<TvDetailResponse>>()
            dummyTvResponse.value = ApiResponse.Success(DataDummy.generateDummyTvDetailResponse())
            `when`(local.getTvDetail(tvId)).thenReturn(dummyTv.asFlow())

            val tvShowDetail = LiveDataTestUtil.getValue(movieTvRepository.getTvShowDetail(tvId).asLiveData())
            verify(local).getTvDetail(tvId)
            Assert.assertNotNull(tvShowDetail)
            assertEquals(detailTvShowResponses.id, tvShowDetail.data?.id)
            assertEquals(detailTvShowResponses.id, tvShowDetail.data?.id)
        }
    }

    @Test
    fun testGetAllFavoriteTv() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Tv>
        `when`(local.getAllFavoriteTv()).thenReturn(dataSourceFactory)
        movieTvRepository.getAllFavoriteTv()

        val favoriteTv =
            Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyListTvEntity()))
        verify(local).getAllFavoriteTv()
        assertNotNull(favoriteTv)
        assertEquals(movieResponses.results.size.toLong(), favoriteTv.data?.size?.toLong())
    }

    @Test
    fun testGetSearchTv() {
        val query = "Avenger"
        val response = MutableLiveData<ApiResponse<List<TvResponse.Result>>>()
        response.value = ApiResponse.Success(DataDummy.generateDummyTvResponse().results)
        `when`(remote.getSearchTv(query)).thenReturn(response.asFlow())

        movieTvRepository.getSearchTv(query)

        val tv = Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyListMovieEntity()))
        verify(remote).getSearchTv(query)
//        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.results.size, tv.data?.size)
    }

    fun testSetFavoriteMovie() {}

    fun testGetFavoriteMovieById() {}

    fun testDeleteFavoriteMovie() {}

    fun testSetFavoriteTv() {}

    fun testGetFavoriteTvById() {}

    fun testDeleteFavoriteTv() {}

    fun testGetCreditsMovie() {}

    fun testGetCreditsTv() {}

    fun testGetVideoMovie() {}

    fun testGetVideoTv() {}

    fun testGetSimilarMovie() {}

    fun testGetSimilarTv() {}

    fun testGetAllGenreMovie() {}

    fun testGetAllGenreTv() {}
}