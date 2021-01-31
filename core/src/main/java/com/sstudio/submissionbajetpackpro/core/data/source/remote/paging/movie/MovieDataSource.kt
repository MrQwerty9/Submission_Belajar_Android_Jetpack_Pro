package com.sstudio.submissionbajetpackpro.core.data.source.remote.paging.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.sstudio.submissionbajetpackpro.core.BuildConfig
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.core.utils.AppExecutors
import com.sstudio.submissionbajetpackpro.core.utils.DataMapper
import com.sstudio.submissionbajetpackpro.core.utils.Params
import com.sstudio.submissionbajetpackpro.core.vo.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieDataSource constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    PageKeyedDataSource<Long, MovieResponse.Result>() {

    var movieParams = Params.MovieParams(null, BuildConfig.TMDB_API_KEY, "en-US", 1)
    var state: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Long?>,
        callback: LoadInitialCallback<Long?, MovieResponse.Result>
    ) {
        state.postValue(NetworkState.LOADING)
        fetchData(movieParams) {
            callback.onResult(it, null, 2)
            appExecutors.diskIO().execute {
                CoroutineScope(Dispatchers.IO).launch {
                    if (state.value == NetworkState.SUCCESS) {
                        localDataSource.deleteAllMovie()
                        Log.d("mytag", "initialsaved")
                    }
                    localDataSource.insertAllMovie(DataMapper.mapMovieResponseToEntities(it))
                }
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, MovieResponse.Result>
    ) {
    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, MovieResponse.Result>
    ) {
        movieParams.page = (params.key + 1).toInt()
        fetchData(movieParams) {
            callback.onResult(it, params.key + 1)
            appExecutors.diskIO().execute {
                CoroutineScope(Dispatchers.IO).launch {
                    localDataSource.insertAllMovie(DataMapper.mapMovieResponseToEntities(it))
                    Log.d("mytag", "aftersaved")
                }
            }
        }
    }

    private fun fetchData(
        movieParams: Params.MovieParams,
        callback: (List<MovieResponse.Result>) -> Unit
    ) {
        state.postValue(NetworkState.LOADING)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                when (val response = remoteDataSource.getAllMovie(movieParams)) {
                    is ApiResponse.Success -> {
                        callback(response.data.results)
                        state.postValue(NetworkState.SUCCESS)
                    }
                    is ApiResponse.Empty -> {
                        callback(listOf())
                        state.postValue(NetworkState(NetworkState.Status.EMPTY, ""))
                    }
                    is ApiResponse.Failed -> {
                        callback(listOf())
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