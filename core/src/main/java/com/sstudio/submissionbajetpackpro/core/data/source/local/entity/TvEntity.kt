package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["id_tv"], unique = true)])
data class TvEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,
    val backdrop_path: String? = "",
    val first_air_date: String? = "",
    val genres_ids: String? = "",
    val id_tv: Int = 0,
    val original_name: String? = "",
    val overview: String? = "",
    val poster_path: String? = "",
    val vote_average: Double? = 0.0
)