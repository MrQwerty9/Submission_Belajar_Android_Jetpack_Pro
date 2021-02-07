package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ListTypeMovie (
    @Embedded
    var movie: MovieEntity,

    @Relation(parentColumn = "idMovie", entityColumn = "idMovie")
    var listTypeEntity: ListTypeMovieEntity?
)