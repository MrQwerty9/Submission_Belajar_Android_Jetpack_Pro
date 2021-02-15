package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MovieList (
    @Embedded
    var movie: MovieEntity,

    @Relation(parentColumn = "id_movie", entityColumn = "id_movie")
    var listEntity: MovieListEntity?
)