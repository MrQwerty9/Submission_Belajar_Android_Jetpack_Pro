package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieListEntity(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int,
    var id_movie: Int,
    var listType: String
)