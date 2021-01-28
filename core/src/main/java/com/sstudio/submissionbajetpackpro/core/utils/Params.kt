package com.sstudio.submissionbajetpackpro.core.utils

object Params {
    var query = ""
    var genre = ""
    var apiKey = ""
    var language = ""

    data class MovieParams(
        val query: String?,
        val apiKey: String,
        val language: String,
        var page: Int
    )

    fun setParams(params: MovieParams){


    }
}