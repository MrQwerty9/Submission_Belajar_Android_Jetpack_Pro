package com.sstudio.submissionbajetpackpro.core.data.source.remote

import android.content.Context
import android.util.Log
import com.sstudio.submissionbajetpackpro.core.BuildConfig
import com.sstudio.submissionbajetpackpro.core.R
import com.sstudio.submissionbajetpackpro.core.data.source.remote.api.ApiService
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.TvResponse
import com.sstudio.submissionbajetpackpro.core.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource constructor(private val apiService: ApiService, context: Context) {

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

    fun getSearchMovie(query: String): Flow<ApiResponse<List<MovieResponse.Result>>> {
        return flow {
            try {
                val response = apiService.getSearchMovie(query, BuildConfig.TMDB_API_KEY, language).results
                if (response.isNotEmpty()){
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

    fun getSearchTv(query: String): Flow<ApiResponse<List<TvResponse.Result>>> {
        return flow {
            try {
                val response = apiService.getSearchTv(query, BuildConfig.TMDB_API_KEY, language).results
                if (response.isNotEmpty()){
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

