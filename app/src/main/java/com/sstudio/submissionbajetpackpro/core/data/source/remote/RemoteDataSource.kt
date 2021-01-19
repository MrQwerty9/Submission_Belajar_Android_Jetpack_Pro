package com.sstudio.submissionbajetpackpro.core.data.source.remote

import android.content.Context
import android.util.Log
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.data.source.remote.api.ApiService
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.TvResponse
import com.sstudio.submissionbajetpackpro.core.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource private constructor(private val apiService: ApiService, context: Context) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService, context: Context): RemoteDataSource =
                instance ?: synchronized(this) {
                    instance ?: RemoteDataSource(apiService, context)
                }
    }
    private val language = context.getString(R.string.language)

    suspend fun getAllMovie(): Flow<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getPopularMovies(BuildConfig.TMDB_API_KEY, language)
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
                EspressoIdlingResource.decrement()
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getAllTvShows(): Flow<ApiResponse<TvResponse>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getPopularTv(BuildConfig.TMDB_API_KEY, language)
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
                EspressoIdlingResource.decrement()
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMovieDetail(movieId: Int): Flow<ApiResponse<MovieResponse.Result>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getMovieDetail(movieId, BuildConfig.TMDB_API_KEY, language)
                if (response.id > 0 ){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
                EspressoIdlingResource.decrement()
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTvShowDetail(tvShowId: Int): Flow<ApiResponse<TvResponse.Result>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getTvDetail(tvShowId, BuildConfig.TMDB_API_KEY, language)
                if (response.id > 0){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
                EspressoIdlingResource.decrement()
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }
}

