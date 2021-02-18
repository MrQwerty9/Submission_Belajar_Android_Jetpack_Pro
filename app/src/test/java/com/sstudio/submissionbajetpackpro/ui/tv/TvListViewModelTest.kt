package com.sstudio.submissionbajetpackpro.ui.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RepoResult
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.core.utils.ListType
import com.sstudio.submissionbajetpackpro.core.utils.Params
import com.sstudio.submissionbajetpackpro.core.vo.NetworkState
import com.sstudio.submissionbajetpackpro.ui.coreTest.data.FakeMovieTvRepository
import com.sstudio.submissionbajetpackpro.ui.coreTest.usecase.FakeMovieTvInteractor
import com.sstudio.submissionbajetpackpro.ui.tv.list.TvListViewModel
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
class TvListViewModelTest {

    private lateinit var viewModel: TvListViewModel
    private lateinit var movieTvUseCase: MovieTvUseCase
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: FakeMovieTvRepository

    @Mock
    private lateinit var observer: Observer<PagedList<Tv>>

    @Mock
    private lateinit var pagedList: PagedList<Tv>

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        movieTvUseCase = FakeMovieTvInteractor(movieTvRepository)
        viewModel = TvListViewModel(movieTvUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testGetTvShow() {
        val params = Params.MovieParams(listType = ListType.POPULAR)
//        val dataTvShows = Resource.Success(pagedList)
        `when`(pagedList.size).thenReturn(5)
        val tvShows = MutableLiveData<PagedList<Tv>>()
        tvShows.value = pagedList
        val state = MutableLiveData<NetworkState>()
        state.value = NetworkState.SUCCESS
        val dataRepo = RepoResult(
            data = tvShows,
            state = state
        )
        `when`(movieTvRepository.getTvShowsList(params)).thenReturn(dataRepo)

        viewModel.setTv(params)
        viewModel.listTv?.observeForever(observer)
        Mockito.verify(movieTvRepository).getTvShowsList(params)
//        Thread.sleep(2000)
        val tvShowEntities = viewModel.listTv?.value
        Assert.assertNotNull(tvShowEntities)
        Assert.assertEquals(5, tvShowEntities?.size)
        Mockito.verify(observer).onChanged(pagedList)
    }
}