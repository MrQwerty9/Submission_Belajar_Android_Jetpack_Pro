package com.sstudio.submissionbajetpackpro.core.domain.model

import com.sstudio.submissionbajetpackpro.core.utils.ListType

data class MovieHome(
    var listType: ListType = ListType.EMPTY,
    var listMovie: ArrayList<Movie> = arrayListOf(Movie())
)