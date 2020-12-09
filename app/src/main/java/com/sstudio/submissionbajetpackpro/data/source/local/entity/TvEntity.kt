package com.sstudio.submissionbajetpackpro.data.source.local.entity

data class TvEntity(
    var backdropPath: String = "",
    var firstAirDate: String = "",
    var genreIds: List<Int>? = ArrayList(),
    var id: Int = 0,
    var originalName: String = "",
    var overview: String = "",
    var posterPath: String = "",
    var voteAverage: Double = 0.0
)