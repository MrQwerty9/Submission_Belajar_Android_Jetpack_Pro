package com.sstudio.submissionbajetpackpro.data.source.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieEntity (
    var backdropPath: String = "",
    var genreIds: List<Int>? = ArrayList(),
    var id: Int = 0,
    var originalTitle: String = "",
    var overview: String = "",
    var posterPath: String = "",
    var releaseDate: String = "",
    var voteAverage: Double = 0.0
):Parcelable