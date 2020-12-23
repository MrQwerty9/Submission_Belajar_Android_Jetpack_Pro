package com.sstudio.submissionbajetpackpro.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_entities")
data class MovieEntity (
    @ColumnInfo(name = "backdropPath")
    var backdropPath: String = "",
    @ColumnInfo(name = "genreIds")
    var genreIds: String = "",
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var id: Int = 0,
    @ColumnInfo(name = "originalTitle")
    var originalTitle: String = "",
    @ColumnInfo(name = "overview")
    var overview: String = "",
    @ColumnInfo(name = "posterPath")
    var posterPath: String = "",
    @ColumnInfo(name = "releaseDate")
    var releaseDate: String = "",
    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double = 0.0
)