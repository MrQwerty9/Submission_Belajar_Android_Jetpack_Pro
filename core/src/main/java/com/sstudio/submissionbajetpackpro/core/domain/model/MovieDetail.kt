package com.sstudio.submissionbajetpackpro.core.domain.model

data class MovieDetail(
    val movie: Movie,
    val adult: Boolean? = null,
    val budget: Int = 0,
    val genres: List<Genre> = listOf(),
    val homepage: String = "",
    val id: Int = 0,
    val imdbId: String = "",
    val originalLanguage: String = "",
    val popularity: Double = 0.0,
    val productionCompanies: List<ProductionCompany> = listOf(),
    val revenue: Int = 0,
    val runtime: Int = 0,
    val status: String = "",
    val title: String = "",
    val voteCount: Int = 0
) {

    data class ProductionCompany(
        val id: Int,
        val logoPath: String?,
        val name: String,
        val originCountry: String
    )
}