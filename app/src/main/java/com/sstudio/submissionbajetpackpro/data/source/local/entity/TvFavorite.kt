package com.sstudio.submissionbajetpackpro.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TvFavorite (
    @Embedded
    var tv: TvEntity,

    @Relation(parentColumn = "tvId", entityColumn = "idMovieTv")
    var favoriteEntity: FavoriteEntity
)