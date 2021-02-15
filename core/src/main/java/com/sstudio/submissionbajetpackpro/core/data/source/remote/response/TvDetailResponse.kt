package com.sstudio.submissionbajetpackpro.core.data.source.remote.response

data class TvDetailResponse(
    val backdrop_path: String? = "",
    val first_air_date: String? = "",
    val genres: List<Genre?>? = listOf(),
    val homepage: String? = "",
    val id: Int = 0,
    val last_air_date: String? = "",
    val name: String? = "",
    val number_of_episodes: Int? = 0,
    val number_of_seasons: Int? = 0,
    val origin_country: List<String?>? = listOf(),
    val original_language: String? = "",
    val original_name: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    val poster_path: String? = "",
    val production_companies: List<ProductionCompany?>? = listOf(),
    val status: String? = "",
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