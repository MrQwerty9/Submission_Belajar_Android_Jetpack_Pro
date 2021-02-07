package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListTypeMovieEntity(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int,
    var idMovie: Int,
    var listType: String
)