package com.sstudio.submissionbajetpackpro.core.data.source.remote.response

data class GenresResponse (
    val genres: List<Genre>
) {
    data class Genre(
        var id: Int? = null,
        var name: String = ""
    )
}