package com.sstudio.submissionbajetpackpro.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class TvResponse(
    @SerializedName("results")
    val results: List<Result> = ArrayList(),
    @SerializedName("total_pages")
    val totalPages: Int? = 0
) {
    data class Result(
        @SerializedName("backdrop_path")
        val backdropPath: String? = "",
        @SerializedName("first_air_date")
        val firstAirDate: String? = "",
        @SerializedName("genre_ids")
        val genreIds: List<Int>? = ArrayList(),
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("original_name")
        val originalName: String? = "",
        @SerializedName("overview")
        val overview: String? = "",
        @SerializedName("poster_path")
        val posterPath: String? = "",
        @SerializedName("vote_average")
        val voteAverage: Double? = 0.0
    )
}