package com.sstudio.submissionbajetpackpro.core.data.source.remote.paging.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.utils.DataMapper
import com.sstudio.submissionbajetpackpro.core.utils.Params
import com.sstudio.submissionbajetpackpro.core.vo.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieBoundaryCallback constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val movieParams: Params.MovieParams
) : PagedList.BoundaryCallback<Movie>() {

    val state = MutableLiveData<NetworkState>()

    init {
        fetchData(movieParams)
    }

    override fun onZeroItemsLoaded() {
        Log.d("mytag", "boundary zero")
        if (movieParams.page > 1)
            fetchData(movieParams)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        Log.d("mytag", "boundary atEndLoaded")
        fetchData(movieParams)
    }

    private fun fetchData(
        movieParams: Params.MovieParams
    ) {
        state.postValue(NetworkState.LOADING)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                when (val response = remoteDataSource.getAllMovie(movieParams)) {
                    is ApiResponse.Success -> {
                        if (movieParams.page == 1){
                            localDataSource.deleteAllMovie()
                        }
                        localDataSource.insertAllMovie(DataMapper.mapMovieResponseToEntities(response.data.results))
                        state.postValue(NetworkState.SUCCESS)
                        movieParams.page++
                    }
                    is ApiResponse.Empty -> {
                        state.postValue(NetworkState(NetworkState.Status.EMPTY, ""))
                    }
                    is ApiResponse.Failed -> {
                        state.postValue(
                            NetworkState(
                                NetworkState.Status.FAILED,
                                response.errorMessage
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                state.postValue(e.message?.let { NetworkState(NetworkState.Status.FAILED, it) })
                Log.e("RemoteDataSource", e.toString())
            }
        }
    }
}