package com.sstudio.submissionbajetpackpro.core.utils

import com.sstudio.submissionbajetpackpro.core.data.source.local.entity.*
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieDetailResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.MovieResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.TvDetailResponse
import com.sstudio.submissionbajetpackpro.core.data.source.remote.response.TvResponse
import com.sstudio.submissionbajetpackpro.core.domain.model.*
import java.util.*

object DataDummy {
    private const val dummyTotalPage = 99

    fun generateDummyListMovieEntity(): List<MovieEntity> {
        val movies = ArrayList<MovieEntity>()
        movies.add(
            MovieEntity(
                backdrop_path = "/jeAQdDX9nguP6YOX6QSWKDPkbBo.jpg",
                genre_ids = "",
                id_movie = 299534,
                original_title = "Avengers: Endgame",
                overview = "Every six years, an ancient order of jiu-jitsu fighters joins forces to battle a vicious race of alien invaders. But when a celebrated war hero goes down in defeat, the fate of the planet and mankind hangs in the balance.",
                poster_path = "/eLT8Cu357VOwBVTitkmlDEg32Fs.jpg",
                release_date = "2020-11-20",
                vote_average = 5.8,
            )
        )
        movies.add(
            MovieEntity(
                0,
                "/ckfwfLkl0CkafTasoRw5FILhZAS.jpg",
                "28, 35, 14",
                602211,
                "Fatman",
                "A rowdy, unorthodox Santa Claus is fighting to save his declining business. Meanwhile, Billy, a neglected and precocious 12 year old, hires a hit man to kill Santa after receiving a lump of coal in his stocking.",
                "/4n8QNNdk4BOX9Dslfbz5Dy6j1HK.jpg",
                "2020-11-13",
                6.1
            )
        )
        movies.add(
            MovieEntity(
                0,
                "/vam9VHLZl8tqNwkp1zjEAxIOrkk.jpg",
                "10751, 14, 10770",
                671583,
                "Upside-Down Magic",
                "Nory and her best friend Reina enter the Sage Academy for Magical Studies, where Nory’s unconventional powers land her in a class for those with wonky, or “upside-down,” magic. Undaunted, Nory sets out to prove that that upside-down magic can be just as powerful as right-side-up.",
                "/xfYMQNApIIh8KhpNVtG1XRz0ZAp.jpg",
                "2020-07-31",
                7.6
            )
        )
        movies.add(
            MovieEntity(
                0,
                "/zKv7KF0pH9ASv2uGgTvTylMlxQn.jpg",
                "37",
                729648,
                "The Dalton Gang",
                "When their brother Frank is killed by an outlaw, brothers Bob Dalton, Emmett Dalton and Gray Dalton join their local sheriff's department. When they are cheated by the law, they turn to crime, robbing trains and anything else they can steal from over the course of two years in the early 1890's. Trying to out do Jesse James, they attempt to rob two banks at once in October of 1892, and things get ugly",
                "/6OeGqp18oZucUGziMIRNhLouZ75.jpg",
                "2020-11-02",
                4.5
            )
        )
        movies.add(
            MovieEntity(
                0,
                "/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
                "28, 53",
                724989,
                "Hard Kill",
                "The work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
                "/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
                "2020-10-23",
                5.0
            )
        )
        return movies
    }

    fun generateDummyListMovie(): List<Movie> =
        generateDummyListMovieEntity().map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }

    fun generateDummyDetailMovieEntity(): MovieDetailEntity =
        MovieDetailEntity(
            id_movie = 590706,
            vote_count = 99,
            production_companies = "",
            original_language = "en",
            imdb_id = "99",
            genres = "",
            status = "release",
            popularity = 9.9,
            id = 1,
            homepage = "",
            title = "movies",
            runtime = 156,
            revenue = 12345,
            budget = 123,
            adult = false
        )

    fun generateDummyTvDetailEntity(): TvDetailEntity =
        TvDetailEntity(
            id_tv = 590706,
            vote_count = 99,
            production_companies = "",
            original_language = "en",
            genres = "",
            status = "release",
            popularity = 9.9,
            id = 1,
            homepage = "",
            name = "movies",
            number_of_seasons = 99,
            number_of_episodes = 9,
            last_air_date = ""
        )

    fun generateDummyListTvEntity(): List<TvEntity> {
        val tvShows = ArrayList<TvEntity>()
        tvShows.add(
            TvEntity(
                0,
                "/9ijMGlJKqcslswWUzTEwScm82Gs.jpg",
                "2019-11-12",
                "10765, 10759, 37",
                82856,
                "The Mandalorian",
                "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
                "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
                8.5
            )
        )
        tvShows.add(
            TvEntity(
                0,
                "/iDbIEpCM9nhoayUDTwqFL1iVwzb.jpg",
                "2017-09-25",
                "18",
                71712,
                "The Good Doctor",
                "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives?",
                "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
                8.6
            )
        )
        tvShows.add(
            TvEntity(
                0,
                "/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg",
                "2005-03-27",
                "18",
                1416,
                "Grey's Anatomy",
                "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
                "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
                8.1
            )
        )
        tvShows.add(
            TvEntity(
                0,
                "/Wu8kh7oyvaIfkNyMJyJHCamh5L.jpg",
                "2020-12-04",
                "18",
                97180,
                "Selena: The Series",
                "As Mexican-American Tejano singer Selena comes of age and realizes her dreams, she and her family make tough choices to hold on to love and music.",
                "/mYsWyfiIMxx4HDm0Wck7oJ9ckez.jpg",
                7.7
            )
        )
        tvShows.add(
            TvEntity(
                0,
                "/6lOtF3yx8iurvaBVz1ZVhwcRgmD.jpg",
                "2017-09-27",
                "10759, 18, 10768",
                71789,
                "SEAL Team",
                "The lives of the elite Navy Seals as they train, plan and execute the most dangerous, high-stakes missions our country can ask.",
                "/uTSLeQTeHevt4fplegmQ6bOnE0Z.jpg",
                7.8
            )
        )
        return tvShows
    }

    fun generateDummyMovieResponse(): MovieResponse {

        val movies = ArrayList<MovieResponse.Result>()
        movies.add(
            MovieResponse.Result(
                "/jeAQdDX9nguP6YOX6QSWKDPkbBo.jpg",
                listOf(28, 14, 878),
                590706,
                "Jiu Jitsu",
                "Every six years, an ancient order of jiu-jitsu fighters joins forces to battle a vicious race of alien invaders. But when a celebrated war hero goes down in defeat, the fate of the planet and mankind hangs in the balance.",
                "/eLT8Cu357VOwBVTitkmlDEg32Fs.jpg",
                "2020-11-20",
                5.8
            )
        )
        movies.add(
            MovieResponse.Result(
                "/ckfwfLkl0CkafTasoRw5FILhZAS.jpg",
                listOf(28, 35, 14),
                602211,
                "Fatman",
                "A rowdy, unorthodox Santa Claus is fighting to save his declining business. Meanwhile, Billy, a neglected and precocious 12 year old, hires a hit man to kill Santa after receiving a lump of coal in his stocking.",
                "/4n8QNNdk4BOX9Dslfbz5Dy6j1HK.jpg",
                "2020-11-13",
                6.1
            )
        )
        movies.add(
            MovieResponse.Result(
                "/vam9VHLZl8tqNwkp1zjEAxIOrkk.jpg",
                listOf(10751, 14, 10770),
                671583,
                "Upside-Down Magic",
                "Nory and her best friend Reina enter the Sage Academy for Magical Studies, where Nory’s unconventional powers land her in a class for those with wonky, or “upside-down,” magic. Undaunted, Nory sets out to prove that that upside-down magic can be just as powerful as right-side-up.",
                "/xfYMQNApIIh8KhpNVtG1XRz0ZAp.jpg",
                "2020-07-31",
                7.6
            )
        )
        movies.add(
            MovieResponse.Result(
                "/zKv7KF0pH9ASv2uGgTvTylMlxQn.jpg",
                listOf(37),
                729648,
                "The Dalton Gang",
                "When their brother Frank is killed by an outlaw, brothers Bob Dalton, Emmett Dalton and Gray Dalton join their local sheriff's department. When they are cheated by the law, they turn to crime, robbing trains and anything else they can steal from over the course of two years in the early 1890's. Trying to out do Jesse James, they attempt to rob two banks at once in October of 1892, and things get ugly",
                "/6OeGqp18oZucUGziMIRNhLouZ75.jpg",
                "2020-11-02",
                4.5
            )
        )
        movies.add(
            MovieResponse.Result(
                "/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
                listOf(28, 53),
                724989,
                "Hard Kill",
                "The work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
                "/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
                "2020-10-23",
                5.0
            )
        )
        return MovieResponse(movies, dummyTotalPage)
    }

    fun generateDummyTvResponse(): TvResponse {
        val tvShows = ArrayList<TvResponse.Result>()
        tvShows.add(
            TvResponse.Result(
                "/9ijMGlJKqcslswWUzTEwScm82Gs.jpg",
                "2019-11-12",
                listOf(10765, 10759, 37),
                82856,
                "The Mandalorian",
                "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
                "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
                8.5
            )
        )
        tvShows.add(
            TvResponse.Result(
                "/iDbIEpCM9nhoayUDTwqFL1iVwzb.jpg",
                "2017-09-25",
                listOf(18),
                71712,
                "The Good Doctor",
                "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives?",
                "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
                8.6
            )
        )
        tvShows.add(
            TvResponse.Result(
                "/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg",
                "2005-03-27",
                listOf(18),
                1416,
                "Grey's Anatomy",
                "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
                "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
                8.1
            )
        )
        tvShows.add(
            TvResponse.Result(
                "/Wu8kh7oyvaIfkNyMJyJHCamh5L.jpg",
                "2020-12-04",
                listOf(18),
                97180,
                "Selena: The Series",
                "As Mexican-American Tejano singer Selena comes of age and realizes her dreams, she and her family make tough choices to hold on to love and music.",
                "/mYsWyfiIMxx4HDm0Wck7oJ9ckez.jpg",
                7.7
            )
        )
        tvShows.add(
            TvResponse.Result(
                "/6lOtF3yx8iurvaBVz1ZVhwcRgmD.jpg",
                "2017-09-27",
                listOf(10759, 18, 10768),
                71789,
                "SEAL Team",
                "The lives of the elite Navy Seals as they train, plan and execute the most dangerous, high-stakes missions our country can ask.",
                "/uTSLeQTeHevt4fplegmQ6bOnE0Z.jpg",
                7.8
            )
        )
        return TvResponse(tvShows, dummyTotalPage)
    }

    fun generateDummyMovieDetail(): MovieDetail {
        return MovieDetail(
            movie = Movie(
                backdropPath = "/jeAQdDX9nguP6YOX6QSWKDPkbBo.jpg",
                genreIds = "",
                id = 299534,
                originalTitle = "Avengers: Endgame",
                overview = "Every six years, an ancient order of jiu-jitsu fighters joins forces to battle a vicious race of alien invaders. But when a celebrated war hero goes down in defeat, the fate of the planet and mankind hangs in the balance.",
                posterPath = "/eLT8Cu357VOwBVTitkmlDEg32Fs.jpg",
                releaseDate = "2020-11-20",
                voteAverage = 5.8,
            ),
            adult = false,
            id = 299534,
            budget = 356000000,
            revenue = 2797800564,
            runtime = 181,
            homepage = "https://www.marvel.com/movies/avengers-endgame",
            popularity = 316.692,
            status = "Released",
            imdbId = "tt4154796",
            originalLanguage = "en",
            productionCompanies = listOf(
                MovieDetail.ProductionCompany(
                    id = 420,
                    logoPath = "/hUzeosd33nzE5MCNsZxCGEKTXaQ.png",
                    name = "Marvel Studios",
                    originCountry = "US"
                )
            ),
            title = "Avengers: Endgame",
            genres = listOf(
                Genre(12, "Adventure"),
                Genre(878, "Science Fiction"),
                Genre(28, "Action")),
            voteCount = 17032
        )
    }

    fun generateDummyTvDetail(): TvDetail {
        return TvDetail(
            tv = Tv(
                backdropPath = "/9ijMGlJKqcslswWUzTEwScm82Gs.jpg",
                genreIds = "",
                id = 82856,
                originalName = "The Mandalorian",
                firstAirDate = "2019-11-12",
                overview = "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
                posterPath = "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
                voteAverage = 8.5
            ),
            popularity = 316.692,
            status = "Released",
            originalLanguage = "en",
            voteCount = 17032,
            genres = listOf(
                Genre(12, "Adventure"),
                Genre(878, "Science Fiction"),
                Genre(28, "Action")),
            id = 82856,
            name = "The Mandalorian",
            productionCompanies = listOf(
                TvDetail.ProductionCompany(
                    id = 420,
                    logoPath = "",
                    name = "",
                    originCountry = ""
                )
            ),
            homepage = "",
            numberOfSeasons = 99,
            numberOfEpisodes = 99,
            lastAirDate = ""
        )
    }

    fun generateDummyTvDetailResponse(): TvDetailResponse {
        return TvDetailResponse(
            backdrop_path = "/9ijMGlJKqcslswWUzTEwScm82Gs.jpg",
            first_air_date = "2019-11-12",
            genres = listOf(
                TvDetailResponse.Genre(12, "Adventure"),
                TvDetailResponse.Genre(878, "Science Fiction"),
                TvDetailResponse.Genre(28, "Action")),
            id = 82856,
            name = "The Mandalorian",
            overview = "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
            poster_path = "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
            vote_average = 8.5
        )
    }

    fun generateDummyFavMovie(): List<MovieFavorite> {
        val favMovie = ArrayList<MovieFavorite>()
        favMovie.add(
            MovieFavorite(
                generateDummyListMovieEntity()[0],
                FavoriteMovieEntity(generateDummyListMovieEntity()[0].id)
            )
        )
        favMovie.add(
            MovieFavorite(
                generateDummyListMovieEntity()[1],
                FavoriteMovieEntity(generateDummyListMovieEntity()[1].id)
            )
        )
        return favMovie
    }

    fun generateDummyFavTv(): ArrayList<TvFavorite> {
        val favTv = ArrayList<TvFavorite>()
        favTv.add(
            TvFavorite(
                generateDummyListTvEntity()[0],
                FavoriteTvEntity(generateDummyListTvEntity()[0].id)
            )
        )
        favTv.add(
            TvFavorite(
                generateDummyListTvEntity()[1],
                FavoriteTvEntity(generateDummyListTvEntity()[1].id)
            )
        )
        return favTv
    }

    fun generateDummyMovieDetailResponse(): MovieDetailResponse =
        MovieDetailResponse(
            adult = false,
            backdrop_path = "/jeAQdDX9nguP6YOX6QSWKDPkbBo.jpg",
            genres = listOf(
                MovieDetailResponse.Genre(12, "Adventure"),
                MovieDetailResponse.Genre(878, "Science Fiction"),
                MovieDetailResponse.Genre(28, "Action")),
            id = 299534,
            title = "Avengers: Endgame",
            overview = "Every six years, an ancient order of jiu-jitsu fighters joins forces to battle a vicious race of alien invaders. But when a celebrated war hero goes down in defeat, the fate of the planet and mankind hangs in the balance.",
            poster_path = "/eLT8Cu357VOwBVTitkmlDEg32Fs.jpg",
            release_date = "2020-11-20",
            vote_average = 5.8,
            budget = 356000000,
            revenue = 2797800564,
            runtime = 181,
            homepage = "https://www.marvel.com/movies/avengers-endgame",
            popularity = 316.692,
            status = "Released",
            imdb_id = "tt4154796",
            original_language = "en",
            production_companies = listOf(
                MovieDetailResponse.ProductionCompany(id = 420, logo_path = "/hUzeosd33nzE5MCNsZxCGEKTXaQ.png", name = "Marvel Studios", origin_country = "US")
            ),
            vote_count = 17032
        )
}