package com.sstudio.submissionbajetpackpro.core.utils

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

object Params {
    var query = ""
    var genre = ""

    @Parcelize
    data class MovieParams(
        val query: String = "",
        var page: Int = 1,
        val listType: ListType,
        val dateGte: String = "",
        val dateLte: String = "",
        val voteCountGte: Int = 0,
        val cast: String = "",
        val genre: String = "",
        val region: String = ""
    ): Parcelable

    fun setParams(params: MovieParams){


    }
}