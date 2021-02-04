package com.sstudio.submissionbajetpackpro.core.data.source.remote

import android.content.Context
import android.util.Log
import com.sstudio.submissionbajetpackpro.core.BuildConfig
import com.sstudio.submissionbajetpackpro.core.R
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.api.ApiService
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.TvResponse
import com.sstudio.submissionbajetpackpro.core.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RemoteDataSource constructor(
    private val apiService: ApiService,
    context: Context,
    private val appExecutors: AppExecutors,
    private val localDataSource: LocalDataSource
) {

    private val language = context.getString(R.string.language)
    private val apiKey = BuildConfig.TMDB_API_KEY

    suspend fun getAllMovieList(movieParams: Params.MovieParams): MovieResponse {
        return try {
            val api = apiService.getPopularMovies(
                apiKey,
                language,
                movieParams.page
            )
            appExecutors.diskIO().execute {
                CoroutineScope(Dispatchers.IO).launch {
                    localDataSource.insertAllMovie(DataMapper.mapMovieResponseToEntities(api.results))
                    Log.d("mytag", "aftersaved")
                }
            }
            api
        } catch (e: Exception) {
            Log.e("RemoteDataSource", e.toString())
            EspressoIdlingResource.decrement()
            MovieResponse()
        }
    }

    suspend fun getAllMovie(movieParams: Params.MovieParams): ApiResponse<MovieResponse> {
        return try {
            val response: MovieResponse = when (movieParams.listType) {
                ListType.POPULAR -> {
                    apiService.getPopularMovies(apiKey, language, movieParams.page)
                }
                ListType.NOW_PLAYING -> {
                    apiService.getNowPlayingMovies(apiKey, language, movieParams.page)
                }
                ListType.TOP_RATED -> {
                    apiService.getTopRatedMovies(apiKey, language, movieParams.page)
                }
                else -> {
                    apiService.getUpcomingMovies(apiKey, language, movieParams.page)
                }
            }
            if (response.results.isNotEmpty()) {
                ApiResponse.Success(response)
            } else {
                ApiResponse.Empty
            }
        } catch (e: Exception) {
            Log.e("RemoteDataSource", e.toString())
            EspressoIdlingResource.decrement()
            ApiResponse.Failed(e.toString())
        }
    }

    suspend fun getAllTvShows(movieParams: Params.MovieParams): ApiResponse<TvResponse> {
        EspressoIdlingResource.increment()
        return try {
            val response: TvResponse = when (movieParams.listType) {
                ListType.POPULAR_TV_SHOW -> {
                    apiService.getPopularTv(apiKey, language, movieParams.page)
                }
                ListType.ON_AIR_TV_SHOW -> {
                    apiService.getOnAirTv(apiKey, language, movieParams.page)
                }
                ListType.TOP_RATED_TV_SHOW -> {
                    apiService.getTopRatedTv(apiKey, language, movieParams.page)
                }
                else -> {
                    apiService.getAiringTodayTv(apiKey, language, movieParams.page)
                }
            }
            val dataArray = response.results
            if (dataArray.isNotEmpty()) {
                ApiResponse.Success(response)
            } else {
                ApiResponse.Empty
            }
        } catch (e: Exception) {
            Log.e("RemoteDataSource", e.toString())
            ApiResponse.Failed(e.toString())
        }
    }

    fun getMovieDetail(movieId: Int): Flow<ApiResponse<MovieResponse.Result>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response =
                    apiService.getMovieDetail(movieId, BuildConfig.TMDB_API_KEY, language)
                if (response.id > 0) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
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
                if (response.id > 0) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getSearchMovie(query: String): Flow<ApiResponse<List<MovieResponse.Result>>> {
        return flow {
            try {
                val response =
                    apiService.getSearchMovie(query, BuildConfig.TMDB_API_KEY, language).results
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getSearchTv(query: String): Flow<ApiResponse<List<TvResponse.Result>>> {
        return flow {
            try {
                val response =
                    apiService.getSearchTv(query, BuildConfig.TMDB_API_KEY, language).results
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }
}

