package com.sstudio.submissionbajetpackpro.core.data.source.remote.response

data class MovieDetailResponse(
    val adult: Boolean? = false,
    val backdrop_path: String? = "",
    val budget: Int? = 0,
    val genres: List<Genre?>? = listOf(),
    val homepage: String? = "",
    val id: Int = 0,
    val imdb_id: String? = "",
    val original_language: String? = "",
    val original_title: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    val poster_path: String? = "",
    val production_companies: List<ProductionCompany?>? = listOf(),
    val release_date: String? = "",
    val revenue: Int? = 0,
    val runtime: Int? = 0,
    val status: String? = "",
    val title: String? = "",
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0
) {

    data class Genre(
        val id: Int? = 0,
        val name: String? = ""
    )

    data class ProductionCompany(
        val id: Int? = 0,
        val logo_path: String? = "",
        val name: String? = "",
        val origin_country: String? = ""
    )
}