package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Relation

data class MovieFavorite (
    @Embedded
    var movie: MovieEntity,

    @Relation(parentColumn = "idMovie", entityColumn = "idMovieTv")
    @NonNull
    var favoriteEntity: FavoriteEntity
)