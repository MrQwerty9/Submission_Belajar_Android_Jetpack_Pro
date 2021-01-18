package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TvEntity(
    var backdropPath: String = "",
    var firstAirDate: String = "",
    var genreIds: String = "",
    @PrimaryKey
    @NonNull
    var id: Int = 0,
    var originalName: String = "",
    var overview: String = "",
    var posterPath: String = "",
    var voteAverage: Double = 0.0
)