package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["idTv"], unique = true)])
data class TvEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,
    var backdropPath: String = "",
    var firstAirDate: String = "",
    var genreIds: String = "",
    var idTv: Int = 0,
    var originalName: String = "",
    var overview: String = "",
    var posterPath: String = "",
    var voteAverage: Double = 0.0
)