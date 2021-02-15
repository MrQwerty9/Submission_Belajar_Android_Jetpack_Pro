package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["id_tv"], unique = true)])
class TvDetailEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,
    val genres: String = "",
    val homepage: String? = "",
    val id_tv: Int? = 0,
    val last_air_date: String? = "",
    val name: String? = "",
    val number_of_episodes: Int? = 0,
    val number_of_seasons: Int? = 0,
    val original_language: String? = "",
    val popularity: Double? = 0.0,
    val production_companies: String = "",
    val status: String? = "",
    val vote_count: Int? = 0
)