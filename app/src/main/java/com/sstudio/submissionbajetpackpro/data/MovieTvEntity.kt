package com.sstudio.submissionbajetpackpro.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieTvEntity (
    var id: Int,
    var title: String,
    var overview: String,
    var poster: Int
):Parcelable