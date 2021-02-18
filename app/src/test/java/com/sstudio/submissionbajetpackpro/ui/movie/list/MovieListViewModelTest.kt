package com.sstudio.submissionbajetpackpro.ui.movie.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RepoResult
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
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
class MovieListViewModelTest {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieTvUseCase: MovieTvUseCase
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: FakeMovieTvRepository

    @Mock
    private lateinit var observer: Observer<PagedList<Movie>>

    @Mock
    private lateinit var pagedList: PagedList<Movie>

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        movieTvUseCase = FakeMovieTvInteractor(movieTvRepository)
        viewModel = MovieListViewModel(movieTvUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    fun testGetState() {

    }

    fun testGetListMovie() {}

    fun testSetMovie() {}

    @Test
    fun testGetMovies() {
        val params = Params.MovieParams(listType = ListType.POPULAR)
        `when`(pagedList.size).thenReturn(5)
        val movies = MutableLiveData<PagedList<Movie>>()
        val state = MutableLiveData<NetworkState>()
        state.value = NetworkState.SUCCESS
        movies.value = pagedList
        val dataRepo = RepoResult(
            data = movies,
            state = state
        )
        `when`(movieTvRepository.getMovieList(params)).thenReturn(dataRepo)
        viewModel.setMovie(params)
        viewModel.listMovie?.observeForever(observer)
//        Thread.sleep(1000)
        val movieEntities = viewModel.listMovie?.value
        Mockito.verify(movieTvRepository).getMovieList(params)
        Assert.assertNotNull(movieEntities)
        Assert.assertEquals(5, movieEntities?.size)
        Mockito.verify(observer).onChanged(pagedList)

    }
}