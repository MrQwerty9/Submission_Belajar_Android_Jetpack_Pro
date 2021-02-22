package com.sstudio.submissionbajetpackpro.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieGenresEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String
)