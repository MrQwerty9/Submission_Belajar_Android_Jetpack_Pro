package com.sstudio.submissionbajetpackpro.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tv(
    var backdropPath: String = "",
    var firstAirDate: String = "",
    var genreIds: List<Int> = listOf(),
    var id: Int = 0,
    var originalName: String = "",
    var overview: String = "",
    var posterPath: String = "",
    var voteAverage: Double = 0.0
): Parcelable