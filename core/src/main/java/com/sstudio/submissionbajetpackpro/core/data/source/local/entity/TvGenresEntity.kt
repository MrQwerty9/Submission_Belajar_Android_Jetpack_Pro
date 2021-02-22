package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TvGenresEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String
)