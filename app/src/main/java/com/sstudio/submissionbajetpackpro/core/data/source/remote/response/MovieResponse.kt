package com.sstudio.submissionbajetpackpro.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val results: List<Result> = ArrayList(),
    @SerializedName("total_pages")
    val totalPages: Int? = 0
) {
    data class Result(
        @SerializedName("backdrop_path")
        val backdropPath: String? = "",
        @SerializedName("genre_ids")
        val genreIds: List<Int>? = ArrayList(),
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("original_title")
        val originalTitle: String? = "",
        @SerializedName("overview")
        val overview: String? = "",
        @SerializedName("poster_path")
        val posterPath: String? = "",
        @SerializedName("release_date")
        val releaseDate: String? = "",
        @SerializedName("vote_average")
        val voteAverage: Double? = 0.0
    )
}