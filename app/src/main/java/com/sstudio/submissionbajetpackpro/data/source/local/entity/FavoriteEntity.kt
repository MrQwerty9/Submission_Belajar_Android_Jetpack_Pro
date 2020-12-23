package com.sstudio.submissionbajetpackpro.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_entities"
)
data class FavoriteEntity(

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idFavorite")
    var idFavorite: Int,

    @NonNull
    @ColumnInfo(name = "idMovieTv")
    var idMovieTv: Int
)