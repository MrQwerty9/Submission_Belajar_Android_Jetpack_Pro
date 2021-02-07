package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteTvEntity(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    var idTv: Int
)