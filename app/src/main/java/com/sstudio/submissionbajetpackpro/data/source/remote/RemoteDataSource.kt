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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun getAllMovie(): LiveData<MovieResponse> {
        val movies = MutableLiveData<MovieResponse>()
        apiService.getPopularMovies(BuildConfig.TMDB_API_KEY, language)
            .enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    movies.postValue(response.body())
                } else {
                    Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable?) {
                Log.e("RemoteDataSource", "onFailure: ${t?.message.toString()}")
            }
        })
        return movies
    }

    fun getAllTvShows(): LiveData<TvResponse> {
        val tvShows = MutableLiveData<TvResponse>()
        apiService.getPopularTv(BuildConfig.TMDB_API_KEY, language)
            .enqueue(object : Callback<TvResponse> {
                override fun onResponse(
                    call: Call<TvResponse>,
                    response: Response<TvResponse>
                ) {
                    if (response.isSuccessful) {
                        tvShows.postValue(response.body())
                    } else {
                        Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<TvResponse>, t: Throwable?) {
                    Log.e("RemoteDataSource", "onFailure: ${t?.message.toString()}")
                }
            })
        return tvShows
    }

    fun getMovieDetail(movieId: Int): LiveData<MovieResponse.Result> {
        val movieDetail = MutableLiveData<MovieResponse.Result>()
        apiService.getMovieDetail(movieId, BuildConfig.TMDB_API_KEY, language)
            .enqueue(object : Callback<MovieResponse.Result> {
                override fun onResponse(
                    call: Call<MovieResponse.Result>,
                    response: Response<MovieResponse.Result>
                ) {
                    if (response.isSuccessful) {
                        movieDetail.postValue(response.body())
                    } else {
                        Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<MovieResponse.Result>, t: Throwable?) {
                    Log.e("RemoteDataSource", "onFailure: ${t?.message.toString()}")
                }
            })
        return movieDetail
    }

    fun getTvShowDetail(tvShowId: Int): LiveData<TvResponse.Result> {
        val tvShowDetail = MutableLiveData<TvResponse.Result>()
        apiService.getTvDetail(tvShowId, BuildConfig.TMDB_API_KEY, language)
            .enqueue(object : Callback<TvResponse.Result> {
                override fun onResponse(
                    call: Call<TvResponse.Result>,
                    response: Response<TvResponse.Result>
                ) {
                    if (response.isSuccessful) {
                        tvShowDetail.postValue(response.body())
                    } else {
                        Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<TvResponse.Result>, t: Throwable?) {
                    Log.e("RemoteDataSource", "onFailure: ${t?.message.toString()}")
                }
            })
        return tvShowDetail
    }


//    fun getModules(courseId: String): List<ModuleResponse> = jsonHelper.loadModule(courseId)
//
//    fun getContent(moduleId: String): ContentResponse = jsonHelper.loadContent(moduleId)

}

