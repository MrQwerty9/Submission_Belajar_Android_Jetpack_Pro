package com.sstudio.submissionbajetpackpro.data.source.remote.api

import androidx.paging.DataSource
import com.sstudio.submissionbajetpackpro.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.data.source.remote.response.TvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<MovieResponse.Result>

    @GET("tv/popular")
    fun getPopularTv(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<TvResponse>

    @GET("tv/{movie_id}")
    fun getTvDetail(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<TvResponse.Result>

    @GET("search/movie/{query}")
    fun getSearchMovie(
        @Path("query") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<DataSource<Int, MovieResponse>>

    @GET("search/tv/{query}")
    fun getSearchTv(
        @Path("query") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<TvResponse>
}