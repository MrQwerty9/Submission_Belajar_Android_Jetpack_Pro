package com.sstudio.submissionbajetpackpro.ui.detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailData(
    var id: Int,
    var title:String,
    var type: Type

) : Parcelable {
    @Parcelize
    enum class Type : Parcelable {
        MOVIE,TV_SHOW
    }
}