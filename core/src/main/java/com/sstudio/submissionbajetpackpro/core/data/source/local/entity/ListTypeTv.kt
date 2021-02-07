package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ListTypeTv (
    @Embedded
    var tv: TvEntity,

    @Relation(parentColumn = "idTv", entityColumn = "idTv")
    var listTypeEntity: ListTypeTvEntity?
)