package com.sstudio.submissionbajetpackpro.ui.movie.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.MovieHome
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.core.utils.DataDummy
import com.sstudio.submissionbajetpackpro.core.utils.ListType
import com.sstudio.submissionbajetpackpro.core.utils.Params
import com.sstudio.submissionbajetpackpro.core.vo.NetworkState
import com.sstudio.submissionbajetpackpro.ui.coreTest.data.FakeMovieTvRepository
import com.sstudio.submissionbajetpackpro.ui.coreTest.usecase.FakeMovieTvInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieHomeViewModelTest {

    private lateinit var viewModel: MovieHomeViewModel
    private lateinit var movieTvUseCase: MovieTvUseCase
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: FakeMovieTvRepository

    @Mock
    private lateinit var observer: Observer<List<Resource<MovieHome>>>

    @Mock
    private lateinit var pagedList: PagedList<Movie>

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        movieTvUseCase = FakeMovieTvInteractor(movieTvRepository)
        viewModel = MovieHomeViewModel(movieTvUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testGetMovies() {
        val params = Params.MovieParams(listType = ListType.POPULAR)
//        `when`(dataMovies.data?.size).thenReturn(5)
        val movies = MutableLiveData<List<Resource<MovieHome>>>()
        val state = MutableLiveData<NetworkState>()
        state.value = NetworkState.SUCCESS
        movies.value = listOf(
            Resource.Success(
                MovieHome(
                    listType = ListType.POPULAR,
                    listMovie = DataDummy.generateDummyListMovie() as ArrayList<Movie>
                )
            ),
            Resource.Success(
                MovieHome(
                    listType = ListType.NOW_PLAYING,
                    listMovie = DataDummy.generateDummyListMovie() as ArrayList<Movie>
                )
            )
        )

        `when`(movieTvRepository.getMovieHome()).thenReturn(movies.asFlow())
        viewModel.listMovie?.observeForever(observer)

        Thread.sleep(2000)
        val movieEntities = viewModel.listMovie?.value
        Mockito.verify(movieTvRepository).getMovieHome()
        Assert.assertNotNull(movieEntities)
        Assert.assertEquals(2, movieEntities?.size)
        Mockito.verify(observer).onChanged(movies.value)

    }
}