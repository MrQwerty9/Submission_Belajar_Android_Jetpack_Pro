package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["id_movie"], unique = true)])
data class MovieEntity (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,
    val backdrop_path: String? = "",
    val genre_ids: String? = "",
    val id_movie: Int = 0,
    val original_title: String? = "",
    val overview: String? = "",
    val poster_path: String? = "",
    val release_date: String? = "",
    val vote_average: Double? = 0.0
)