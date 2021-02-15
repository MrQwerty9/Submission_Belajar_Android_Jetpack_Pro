package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TvFavorite (
    @Embedded
    var tv: TvEntity,

    @Relation(parentColumn = "id_tv", entityColumn = "idTv")
    var favoriteEntity: FavoriteTvEntity?
)