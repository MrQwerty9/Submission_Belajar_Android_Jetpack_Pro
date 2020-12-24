package com.sstudio.submissionbajetpackpro.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity (
    var backdropPath: String = "",
    var genreIds: String = "",
    @PrimaryKey
    @NonNull
    var id: Int = 0,
    var originalTitle: String = "",
    var overview: String = "",
    var posterPath: String = "",
    var releaseDate: String = "",
    var voteAverage: Double = 0.0
)