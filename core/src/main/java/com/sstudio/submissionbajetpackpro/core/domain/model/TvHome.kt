package com.sstudio.submissionbajetpackpro.core.domain.model

import com.sstudio.submissionbajetpackpro.core.utils.ListType

data class TvHome(
    var listType: ListType = ListType.EMPTY,
    var listTv: ArrayList<Tv> = arrayListOf(Tv())
)