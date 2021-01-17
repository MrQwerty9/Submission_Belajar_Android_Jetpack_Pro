package com.sstudio.submissionbajetpackpro.data.source.remote

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.data.source.remote.api.ApiService
import com.sstudio.submissionbajetpackpro.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.data.source.remote.response.TvResponse
import com.sstudio.submissionbajetpackpro.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService, context: Context) {

    private val language = context.getString(R.string.language)

    fun getAllMovie(): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<MovieResponse>>()
        apiService.getPopularMovies(BuildConfig.TMDB_API_KEY, language)
            .enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { resultMovie.value = ApiResponse.success(it) }
                } else {
                    Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable?) {
                Log.e("RemoteDataSource", "onFailure: ${t?.message.toString()}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultMovie
    }

    fun getAllTvShows(): LiveData<ApiResponse<TvResponse>> {
        EspressoIdlingResource.increment()
        val resultTv = MutableLiveData<ApiResponse<TvResponse>>()
        apiService.getPopularTv(BuildConfig.TMDB_API_KEY, language)
            .enqueue(object : Callback<TvResponse> {
                override fun onResponse(
                    call: Call<TvResponse>,
                    response: Response<TvResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { resultTv.value = ApiResponse.success(it) }
                    } else {
                        Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TvResponse>, t: Throwable?) {
                    Log.e("RemoteDataSource", "onFailure: ${t?.message.toString()}")
                    EspressoIdlingResource.decrement()
                }
            })
        return resultTv
    }

    fun getMovieDetail(movieId: Int): LiveData<ApiResponse<MovieResponse.Result>> {
        EspressoIdlingResource.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<MovieResponse.Result>>()
        apiService.getMovieDetail(movieId, BuildConfig.TMDB_API_KEY, language)
            .enqueue(object : Callback<MovieResponse.Result> {
                override fun onResponse(
                    call: Call<MovieResponse.Result>,
                    response: Response<MovieResponse.Result>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { resultDetailMovie.value = ApiResponse.success(it) }
                    } else {
                        Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<MovieResponse.Result>, t: Throwable?) {
                    Log.e("RemoteDataSource", "onFailure: ${t?.message.toString()}")
                    EspressoIdlingResource.decrement()
                }
            })
        return resultDetailMovie
    }

    fun getTvShowDetail(tvShowId: Int): LiveData<ApiResponse<TvResponse.Result>> {
        EspressoIdlingResource.increment()
        val resultDetailTv = MutableLiveData<ApiResponse<TvResponse.Result>>()
        apiService.getTvDetail(tvShowId, BuildConfig.TMDB_API_KEY, language)
            .enqueue(object : Callback<TvResponse.Result> {
                override fun onResponse(
                    call: Call<TvResponse.Result>,
                    response: Response<TvResponse.Result>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { resultDetailTv.value = ApiResponse.success(it) }
                    } else {
                        Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TvResponse.Result>, t: Throwable?) {
                    Log.e("RemoteDataSource", "onFailure: ${t?.message.toString()}")
                    EspressoIdlingResource.decrement()
                }
            })
        return resultDetailTv
    }
}

