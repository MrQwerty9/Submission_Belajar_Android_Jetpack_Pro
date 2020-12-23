package com.sstudio.submissionbajetpackpro.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_entities")
data class TvEntity(
    @ColumnInfo(name = "backdropPath")
    var backdropPath: String = "",
    @ColumnInfo(name = "firstAirDate")
    var firstAirDate: String = "",
    @ColumnInfo(name = "genreIds")
    var genreIds: String = "",
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvId")
    var id: Int = 0,
    @ColumnInfo(name = "originalName")
    var originalName: String = "",
    @ColumnInfo(name = "overview")
    var overview: String = "",
    @ColumnInfo(name = "posterPath")
    var posterPath: String = "",
    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double = 0.0
)