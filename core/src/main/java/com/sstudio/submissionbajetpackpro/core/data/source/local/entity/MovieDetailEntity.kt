package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["id_movie"], unique = true)])
data class MovieDetailEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,
    val adult: Boolean? = null,
    val budget: Long = 0,
    val genres: String? = "",
    val homepage: String? = "",
    val id_movie: Int = 0,
    val imdb_id: String? = "",
    val original_language: String? = "",
    val popularity: Double? = 0.0,
    val production_companies: String? = "",
    val revenue: Long = 0,
    val runtime: Int? = 0,
    val status: String? = "",
    val title: String? = "",
    val vote_count: Int? = 0
)