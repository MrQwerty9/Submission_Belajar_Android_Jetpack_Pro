package com.sstudio.submissionbajetpackpro.core.data.source.remote

import android.content.Context
import android.util.Log
import com.sstudio.submissionbajetpackpro.core.BuildConfig
import com.sstudio.submissionbajetpackpro.core.R
import com.sstudio.submissionbajetpackpro.core.data.source.remote.api.ApiService
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.*
import com.sstudio.submissionbajetpackpro.core.utils.ListType
import com.sstudio.submissionbajetpackpro.core.utils.Params
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(
    private val apiService: ApiService,
    context: Context
) {

    private val language = context.getString(R.string.language)
    private val apiKey = BuildConfig.TMDB_API_KEY

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
            ApiResponse.Failed(e.toString())
        }
    }

    suspend fun getAllTvShows(movieParams: Params.MovieParams): ApiResponse<TvResponse> {
        
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

    fun getMovieDetail(movieId: Int): Flow<ApiResponse<MovieDetailResponse>> {
        
        return flow {
            try {
                val response =
                    apiService.getMovieDetail(movieId, BuildConfig.TMDB_API_KEY, language)
                if (response.id > 0) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTvShowDetail(tvShowId: Int): Flow<ApiResponse<TvDetailResponse>> {
        
        return flow {
            try {
                val response = apiService.getTvDetail(tvShowId, BuildConfig.TMDB_API_KEY, language)
                if (response.id > 0) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getSearchMovie(query: String): Flow<ApiResponse<List<MovieResponse.Result>>> =
        flow {
            try {
                val response =
                    apiService.getSearchMovie(query, BuildConfig.TMDB_API_KEY, language).results
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getSearchTv(query: String): Flow<ApiResponse<List<TvResponse.Result>>> =
        flow {
            try {
                val response =
                    apiService.getSearchTv(query, BuildConfig.TMDB_API_KEY, language).results
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getCreditsMovie(movieId: Int): Flow<ApiResponse<CreditsResponse>> =
        flow {
            try {
                val response =
                    apiService.getCreditsMovie(movieId, BuildConfig.TMDB_API_KEY, language)
                if (response.cast.isNotEmpty() || response.crew.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    
    fun getCreditsTv(tvId: Int): Flow<ApiResponse<CreditsResponse>> =
        flow {
            try {
                val response =
                    apiService.getCreditsTv(tvId, BuildConfig.TMDB_API_KEY, language)
                if (response.cast.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    
    fun getVideoMovie(movieId: Int): Flow<ApiResponse<VideoResponse>> =
        flow {
            try {
                val response =
                    apiService.getVideoMovie(movieId, BuildConfig.TMDB_API_KEY, language)
                if (response.video.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    
    fun getVideoTv(tvId: Int): Flow<ApiResponse<VideoResponse>> =
        flow {
            try {
                val response =
                    apiService.getVideoTv(tvId, BuildConfig.TMDB_API_KEY, language)
                if (response.video.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    
    fun getSimilarMovie(movieId: Int): Flow<ApiResponse<MovieResponse>> =
        flow {
            try {
                val response =
                    apiService.getSimilarMovie(movieId, BuildConfig.TMDB_API_KEY, language)
                if (response.results.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    
    fun getSimilarTv(tvId: Int): Flow<ApiResponse<TvResponse>> =
        flow {
            try {
                val response =
                    apiService.getSimilarTv(tvId, BuildConfig.TMDB_API_KEY, language)
                if (response.results.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getAllGenreMovie(): Flow<ApiResponse<GenresResponse>> =
        flow {
            try {
                val response =
                    apiService.getAllGenreMovie(BuildConfig.TMDB_API_KEY, language)
                if (response.genres.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    
    fun getAllGenreTv(): Flow<ApiResponse<GenresResponse>> =
        flow {
            try {
                val response =
                    apiService.getAllGenreTv(BuildConfig.TMDB_API_KEY, language)
                if (response.genres.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Failed(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
}

