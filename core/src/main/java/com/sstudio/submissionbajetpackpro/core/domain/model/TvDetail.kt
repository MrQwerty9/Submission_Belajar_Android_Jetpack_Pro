package com.sstudio.submissionbajetpackpro.core.domain.model

data class TvDetail(
    val tv: Tv,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val lastAirDate: String,
    val name: String,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val originalLanguage: String,
    val popularity: Double,
    val productionCompanies: List<ProductionCompany>,
    val status: String,
    val voteCount: Int
) {

    data class ProductionCompany(
        val id: Int,
        val logoPath: String,
        val name: String,
        val originCountry: String
    )
}