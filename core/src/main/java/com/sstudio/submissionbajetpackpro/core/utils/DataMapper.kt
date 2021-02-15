package com.sstudio.submissionbajetpackpro.core.utils

import androidx.paging.DataSource
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.*
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.*
import com.sstudio.submissionbajetpackpro.core.domain.model.*
import java.lang.reflect.Type


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
            backdrop_path = response.backdropPath ?: "",
            genre_ids = response.genreIds?.joinToString(separator = ",") ?: "",
            id_movie = response.id,
            original_title = response.originalTitle ?: "",
            overview = response.overview ?: "",
            poster_path = response.posterPath ?: "",
            release_date = response.releaseDate ?: "",
            vote_average = response.voteAverage ?: 0.0
        )

    fun mapMovieEntitiesToDomain(entity: MovieEntity): Movie =
        Movie(
            backdropPath = entity.backdrop_path ?: "",
            genreIds = entity.genre_ids ?: "",
            id = entity.id_movie,
            originalTitle = entity.original_title ?: "",
            overview = entity.overview ?: "",
            posterPath = entity.poster_path ?: "",
            releaseDate = entity.release_date ?: "",
            voteAverage = entity.vote_average ?: 0.0
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
            backdrop_path = response.backdropPath ?: "",
            genres_ids = response.genreIds?.joinToString(separator = ",") ?: "",
            id_tv = response.id,
            original_name = response.originalName ?: "",
            overview = response.overview ?: "",
            poster_path = response.posterPath ?: "",
            first_air_date = response.firstAirDate ?: "",
            vote_average = response.voteAverage ?: 0.0
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
            backdropPath = entity.backdrop_path ?: "",
            genreIds = entity.genres_ids ?: "",
            id = entity.id_tv,
            originalName = entity.original_name ?: "",
            overview = entity.overview ?: "",
            posterPath = entity.poster_path ?: "",
            firstAirDate = entity.first_air_date ?: "",
            voteAverage = entity.vote_average ?: 0.0
        )

    fun mapCreditsResponseToDomain(creditsResponse: CreditsResponse): Credits =
        Credits(
            creditsResponse.id,
            creditsResponse.cast.map {
                Credits.Cast(
                    adult = it.adult,
                    gender = it.gender,
                    id = it.id,
                    knownForDepartment = it.known_for_department ?: "",
                    name = it.name ?: "",
                    originalName = it.original_name ?: "",
                    popularity = it.popularity ?: 0.0,
                    profilePath = it.profile_path ?: "",
                    castId = it.cast_id ?: 0,
                    character = it.character ?: "",
                    creditId = it.credit_id ?: "",
                    order = it.order ?: 0
                )
            },
            creditsResponse.crew.map {
                Credits.Crew(
                    adult = it.adult,
                    gender = it.gender,
                    id = it.id,
                    knownForDepartment = it.known_for_department ?: "",
                    name = it.name ?: "",
                    originalName = it.original_name ?: "",
                    popularity = it.popularity ?: 0.0,
                    profilePath = it.profile_path ?: "",
                    creditId = it.credit_id ?: "",
                    department = it.department ?: "",
                    job = it.job ?: ""
                )
            }
        )

    fun mapVideoResponseToDomain(response: VideoResponse.Video): Video =
        Video(
            id = response.id,
            key = response.key,
            name = response.name
        )

    fun mapGenreResponseToDomain(response: GenresResponse.Genre): Genre =
        Genre(
            id = response.id,
            name = response.name
        )

    fun mapMovieDetailResponseToDomain(response: MovieDetailResponse): MovieDetail =
        MovieDetail(
            movie = Movie(
                backdropPath = response.backdrop_path ?: "",
                originalTitle = response.original_title ?: "",
                overview = response.overview ?: "",
                posterPath = response.poster_path ?: "",
                releaseDate = response.release_date ?: "",
                voteAverage = response.vote_average ?: 0.0
            ),
            adult = response.adult ?: false,
            budget = response.budget ?: 0,
            genres = response.genres?.map {
                Genre(
                    it?.id ?: 0,
                    it?.name ?: ""
                )
            } ?: listOf(),
            homepage = response.homepage ?: "",
            id = response.id,
            imdbId = response.imdb_id ?: "",
            originalLanguage = response.original_language ?: "",
            popularity = response.popularity ?: 0.0,
            productionCompanies = response.production_companies?.map {
                MovieDetail.ProductionCompany(
                    it?.id ?: 0,
                    it?.logo_path ?: "",
                    it?.name ?: "",
                    it?.origin_country ?: ""
                )
            } ?: listOf(),
            revenue = response.revenue ?: 0,
            runtime = response.runtime ?: 0,
            status = response.status ?: "",
            title = response.title ?: "",
            voteCount = response.vote_count ?: 0
        )

    fun mapTvDetailResponseToDomain(response: TvDetailResponse): TvDetail =
        TvDetail(
            tv = Tv(
                backdropPath = response.backdrop_path ?: "",
                firstAirDate = response.first_air_date ?: "",
                originalName = response.original_name ?: "",
                overview = response.overview ?: "",
                posterPath = response.poster_path ?: "",
                voteAverage = response.vote_average ?: 0.0
            ),
            genres = response.genres?.map {
                Genre(
                    id = it?.id ?: 0,
                    name = it?.name ?: ""
                )
            } ?: listOf(),
            homepage = response.homepage ?: "",
            id = response.id,
            lastAirDate = response.last_air_date ?: "",
            name = response.name ?: "",
            numberOfEpisodes = response.number_of_episodes ?: 0,
            numberOfSeasons = response.number_of_seasons ?: 0,
            originalLanguage = response.original_language ?: "",
            popularity = response.popularity ?: 0.0,
            productionCompanies = response.production_companies?.map {
                TvDetail.ProductionCompany(
                    it?.id ?: 0,
                    it?.logo_path ?: "",
                    it?.name ?: "",
                    it?.origin_country ?: ""
                )
            } ?: listOf(),
            status = response.status ?: "",
            voteCount = response.vote_count ?: 0
        )

    fun mapMovieDetailResponseToMovieEntity(response: MovieDetailResponse): MovieEntity =
        MovieEntity(
            backdrop_path = response.backdrop_path,
            genre_ids = response.genres?.map { it?.id }?.joinToString(separator = ",") ?: "",
            id_movie = response.id,
            original_title = response.original_title,
            overview = response.overview,
            poster_path = response.poster_path,
            release_date = response.release_date,
            vote_average = response.vote_average
        )

    fun mapTvDetailResponseToTvEntities(response: TvDetailResponse): TvEntity =
        TvEntity(
            backdrop_path = response.backdrop_path ?: "",
            genres_ids = response.genres?.map { it?.id }?.joinToString(separator = ",") ?: "",
            id_tv = response.id,
            original_name = response.original_name ?: "",
            overview = response.overview ?: "",
            poster_path = response.poster_path ?: "",
            first_air_date = response.first_air_date ?: "",
            vote_average = response.vote_average ?: 0.0
        )

    fun mapMovieDetailResponseToEntities(response: MovieDetailResponse): MovieDetailEntity =
        MovieDetailEntity(
            adult = response.adult ?: false,
            budget = response.budget ?: 0,
            genres = response.genres?.let {
                mapperListObjToStr(it)
            } ?: "",
            homepage = response.homepage ?: "",
            id_movie = response.id,
            imdb_id = response.imdb_id ?: "",
            original_language = response.original_language ?: "",
            popularity = response.popularity ?: 0.0,
            production_companies = response.production_companies?.let {
                mapperListObjToStr(it)
            } ?: "",
            revenue = response.revenue ?: 0,
            runtime = response.runtime ?: 0,
            status = response.status ?: "",
            title = response.title ?: "",
            vote_count = response.vote_count ?: 0
        )

    fun mapTvDetailResponseToEntities(response: TvDetailResponse): TvDetailEntity =
        TvDetailEntity(
            genres = response.genres?.let {
                mapperListObjToStr(it)
            } ?: "",
            homepage = response.homepage ?: "",
            id_tv = response.id,
            last_air_date = response.last_air_date ?: "",
            name = response.name ?: "",
            number_of_episodes = response.number_of_episodes ?: 0,
            number_of_seasons = response.number_of_seasons ?: 0,
            original_language = response.original_language ?: "",
            popularity = response.popularity ?: 0.0,
            production_companies = response.production_companies?.let {
                mapperListObjToStr(it)
            } ?: "",
            status = response.status ?: "",
            vote_count = response.vote_count ?: 0
        )

    fun mapMovieDetailEntitiesToDomain(entity: MovieDetailEmbed?): MovieDetail =
        MovieDetail(
            movie = Movie(
                backdropPath = entity?.movie?.backdrop_path ?: "",
                originalTitle = entity?.movie?.original_title ?: "",
                genreIds = entity?.movie?.genre_ids ?: "",
                overview = entity?.movie?.overview ?: "",
                posterPath = entity?.movie?.poster_path ?: "",
                releaseDate = entity?.movie?.release_date ?: "",
                voteAverage = entity?.movie?.vote_average ?: 0.0
            ),
            adult = entity?.detailEntity?.adult ?: false,
            budget = entity?.detailEntity?.budget ?: 0,
            genres = entity?.detailEntity?.genres?.let { mapperStrObjToList(it, object : TypeToken<List<Genre>>() {}.type) }
                ?: listOf(),
            homepage = entity?.detailEntity?.homepage ?: "",
            id = entity?.detailEntity?.id_movie ?: 0,
            imdbId = entity?.detailEntity?.imdb_id ?: "",
            originalLanguage = entity?.detailEntity?.original_language ?: "",
            popularity = entity?.detailEntity?.popularity ?: 0.0,
            productionCompanies = entity?.detailEntity?.production_companies?.let {
                mapperStrObjToList(it, object : TypeToken<List<MovieDetail.ProductionCompany>>() {}.type)
            } ?: listOf(),
            revenue = entity?.detailEntity?.revenue ?: 0,
            runtime = entity?.detailEntity?.runtime ?: 0,
            status = entity?.detailEntity?.status ?: "",
            title = entity?.detailEntity?.title ?: "",
            voteCount = entity?.detailEntity?.vote_count ?: 0
        )

    fun mapTvDetailEntitiesToDomain(entity: TvDetailEmbed?): TvDetail =
        TvDetail(
            tv = Tv(
                backdropPath = entity?.tv?.backdrop_path ?: "",
                genreIds = entity?.tv?.genres_ids ?: "",
                firstAirDate = entity?.tv?.first_air_date ?: "",
                originalName = entity?.tv?.original_name ?: "",
                overview = entity?.tv?.overview ?: "",
                posterPath = entity?.tv?.poster_path ?: "",
                voteAverage = entity?.tv?.vote_average ?: 0.0
            ),
            genres = entity?.detailEntity?.genres?.let { mapperStrObjToList(it, object : TypeToken<List<Genre>>() {}.type) } ?: listOf(),
            homepage = entity?.detailEntity?.homepage ?: "",
            id = entity?.detailEntity?.id_tv ?: 0,
            lastAirDate = entity?.detailEntity?.last_air_date ?: "",
            name = entity?.detailEntity?.name ?: "",
            numberOfEpisodes = entity?.detailEntity?.number_of_episodes ?: 0,
            numberOfSeasons = entity?.detailEntity?.number_of_seasons ?: 0,
            originalLanguage = entity?.detailEntity?.original_language ?: "",
            popularity = entity?.detailEntity?.popularity ?: 0.0,
            productionCompanies = entity?.detailEntity?.production_companies?.let {
                mapperStrObjToList(it, object : TypeToken<List<TvDetail.ProductionCompany>>() {}.type)
            } ?: listOf(),
            status = entity?.detailEntity?.status ?: "",
            voteCount = entity?.detailEntity?.vote_count ?: 0
        )

    @TypeConverter
    fun <T>mapperListObjToStr(value: List<T>): String {
        return try {
            val gson = Gson()
            val type = object : TypeToken<List<T>>() {}.type
            gson.toJson(value, type)
        }catch (e: Exception){
            ""
        }
    }

    inline fun <reified T> mapperStrObjToList(json: String, typeToken: Type): T {
        val gson = GsonBuilder().create()
        return gson.fromJson<T>(json, typeToken)
    }
}