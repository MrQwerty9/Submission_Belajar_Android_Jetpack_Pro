package com.sstudio.submissionbajetpackpro.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie (
    var backdropPath: String = "",
    var genreIds: List<Int> = listOf(),
    var id: Int = 0,
    var originalTitle: String = "",
    var overview: String = "",
    var posterPath: String = "",
    var releaseDate: String = "",
    var voteAverage: Double = 0.0
): Parcelable