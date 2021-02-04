package com.sstudio.submissionbajetpackpro.core.utils

enum class ListType {
    EMPTY,POPULAR_PERSON,PERSON_SEARCH,MOVIE_SEARCH,TV_SEARCH,SMILER,GENRE,MOVIE_CREDITS,
    TV_CREDITS,TRENDING_MOVIE,TRENDING_TV_SHOW,TRENDING_PERSON,NOW_PLAYING,
    UPCOMING,POPULAR,POPULAR_TV_SHOW,TOP_RATED,TOP_RATED_TV_SHOW,CAST,CREW,BOOKMARK_MOVIE,BOOKMARK_TV_SHOW,BOOKMARK_PERSON,
    MY_LIST_TV_SHOW,MY_LIST_MOVIE, ON_AIR_TV_SHOW, AIRING_TODAY
}

object SortType{
    val POPULAR = "popularity.desc"
    val NOW_PLAYING = "now_playing"
    val TOP_RATED = "vote_average.desc"
    val UPCOMING = "upcoming"

    val AIRING_TODAY = "airing_today"
    val ON_THE_AIR = "on_the_air"
}