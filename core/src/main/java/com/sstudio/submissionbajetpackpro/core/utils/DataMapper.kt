package com.sstudio.submissionbajetpackpro.core.utils

import androidx.paging.DataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.MovieEntity
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.TvEntity
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.TvResponse
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.domain.model.Tv

object DataMapper {
    fun mapMovieResponseToDomain(response: MovieResponse.Result): Movie =
            Movie(
                backdropPath = response.backdropPath ?: "",
                genreIds = response.genreIds?.joinToString(separator = ",") ?: "",
                id = response.id,
                originalTitle = response.originalTitle ?: "",
                overview = response.overview ?: "",
                posterPath = response.posterPath ?: "",
                releaseDate = response.releaseDate ?: "",
                voteAverage = response.voteAverage ?: 0.0
            )

    fun mapMovieResponseToEntities(response: MovieResponse.Result): MovieEntity =
            MovieEntity(
                backdropPath = response.backdropPath ?: "",
                genreIds = response.genreIds?.joinToString(separator = ",") ?: "",
                idMovie = response.id,
                originalTitle = response.originalTitle ?: "",
                overview = response.overview ?: "",
                posterPath = response.posterPath ?: "",
                releaseDate = response.releaseDate ?: "",
                voteAverage = response.voteAverage ?: 0.0
            )

    fun mapMovieEntitiesToDomain(entity: MovieEntity): Movie =
            Movie(
                backdropPath = entity.backdropPath,
                genreIds = entity.genreIds,
                id = entity.idMovie,
                originalTitle = entity.originalTitle,
                overview = entity.overview,
                posterPath = entity.posterPath,
                releaseDate = entity.releaseDate,
                voteAverage = entity.voteAverage
            )

    fun mapTvResponseToDomain(response: TvResponse.Result): Tv =
            Tv(
                backdropPath = response.backdropPath ?: "",
                genreIds = response.genreIds?.joinToString(separator = ",") ?: "",
                id = response.id,
                originalName = response.originalName ?: "",
                overview = response.overview ?: "",
                posterPath = response.posterPath ?: "",
                firstAirDate = response.firstAirDate ?: "",
                voteAverage = response.voteAverage ?: 0.0
            )

    fun mapTvResponseToEntities(response: TvResponse.Result): TvEntity =
            TvEntity(
                backdropPath = response.backdropPath ?: "",
                genreIds = response.genreIds?.joinToString(separator = ",") ?: "",
                idTv = response.id,
                originalName = response.originalName ?: "",
                overview = response.overview ?: "",
                posterPath = response.posterPath ?: "",
                firstAirDate = response.firstAirDate ?: "",
                voteAverage = response.voteAverage ?: 0.0
            )

    fun mapTvEntitiesPagingToDomain(input: DataSource.Factory<Int, Tv>): DataSource.Factory<Int, Tv> =
        input.map {
            Tv(
                backdropPath = it.backdropPath,
                genreIds = it.genreIds,
                id = it.id,
                originalName = it.originalName,
                overview = it.overview,
                posterPath = it.posterPath,
                firstAirDate = it.firstAirDate,
                voteAverage = it.voteAverage
            )
        }

    fun mapTvEntitiesToDomain(entity: TvEntity): Tv =
        Tv(
            backdropPath = entity.backdropPath,
            genreIds = entity.genreIds,
            id = entity.idTv,
            originalName = entity.originalName,
            overview = entity.overview,
            posterPath = entity.posterPath,
            firstAirDate = entity.firstAirDate,
            voteAverage = entity.voteAverage
        )

    fun mapMovieDomainToEntity(movie: Movie): MovieEntity =
        MovieEntity(
            backdropPath = movie.backdropPath,
            genreIds = movie.genreIds,
            id = movie.id,
            originalTitle = movie.originalTitle,
            overview = movie.overview,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage
        )

//    fun mapMovieFavoriteToDomainPagedList(input: DataSource.Factory<Int, MovieFavorite>): DataSource.Factory<Int, Movie> =
//        input.map{
//            Movie(
//                id = it.movie.id,
//                channel = it.movie.channel,
//                logoPath = it.movie.logoPath
//            )
//        }
}