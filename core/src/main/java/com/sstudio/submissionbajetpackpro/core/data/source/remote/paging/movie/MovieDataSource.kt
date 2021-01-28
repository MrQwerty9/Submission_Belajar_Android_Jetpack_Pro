package com.sstudio.submissionbajetpackpro.core.data.source.remote.paging.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.paging.PageKeyedDataSource
import com.sstudio.submissionbajetpackpro.core.BuildConfig
import com.sstudio.submissionbajetpackpro.core.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.core.utils.Params
import com.sstudio.submissionbajetpackpro.core.vo.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieDataSource constructor(private val remoteDataSource: RemoteDataSource) :
    PageKeyedDataSource<Long, ApiResponse<MovieResponse>>() {

    var movieParams = Params.MovieParams(null, BuildConfig.TMDB_API_KEY, "en-US", 1)
    var state: MutableLiveData<NetworkState> = MutableLiveData()

    fun getState() = state.asFlow()

    fun setState() {
        state.postValue(NetworkState.LOADING)
    }

    override fun loadInitial(
        params: LoadInitialParams<Long?>,
        callback: LoadInitialCallback<Long?, ApiResponse<MovieResponse>>
    ) {
        state.postValue(NetworkState.LOADING)
        fetchData(movieParams){
            callback.onResult(it, null, 2)
        }
    }

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, ApiResponse<MovieResponse>>
    ) {
    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, ApiResponse<MovieResponse>>
    ) {
        movieParams.page = (params.key + 1).toInt()
        fetchData(movieParams){
            callback.onResult(it, params.key + 1)
        }
    }

    private fun fetchData(movieParams: Params.MovieParams, callback: (List<ApiResponse<MovieResponse>>) -> Unit) {
        state.postValue(NetworkState.LOADING)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                when (val response = remoteDataSource.getAllMovie(movieParams)){
                    is ApiResponse.Success -> {
                        callback(listOf(ApiResponse.Success(response.data)))
                        state.postValue(NetworkState.SUCCESS)
                    }
                    is ApiResponse.Empty -> {
                        callback(listOf(ApiResponse.Empty))
                        state.postValue(NetworkState(NetworkState.Status.EMPTY,""))
                    }
                    is ApiResponse.Failed -> {
                        callback(listOf(ApiResponse.Failed(response.errorMessage)))
                        state.postValue(NetworkState(NetworkState.Status.FAILED, response.errorMessage))
                    }
                }

            } catch (e: Exception) {
                state.postValue(e.message?.let { NetworkState(NetworkState.Status.FAILED, it) })
                Log.e("RemoteDataSource", e.toString())
            }
        }
    }
}