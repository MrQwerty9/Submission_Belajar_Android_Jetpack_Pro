package com.sstudio.submissionbajetpackpro.core.utils

enum class ListType(private val strQuery: String) {
    EMPTY(""),
    POPULAR_PERSON(""),
    PERSON_SEARCH(""),
    MOVIE_SEARCH(""),
    TV_SEARCH(""),
    SMILER(""),
    GENRE(""),
    MOVIE_CREDITS(""),
    TV_CREDITS(""),
    TRENDING_MOVIE(""),
    TRENDING_TV_SHOW(""),
    TRENDING_PERSON(""),
    NOW_PLAYING("now_playing"),
    UPCOMING("upcoming"),
    POPULAR("popularity.desc"),
    POPULAR_TV_SHOW("popularity.desc"),
    TOP_RATED("vote_average.desc"),
    TOP_RATED_TV_SHOW("vote_average.desc"),
    ON_AIR_TV_SHOW("on_the_air"),
    AIRING_TODAY("airing_today"),
    CAST(""),
    CREW(""),
    BOOKMARK_MOVIE(""),
    BOOKMARK_TV_SHOW(""),
    BOOKMARK_PERSON(""),
    MY_LIST_TV_SHOW(""),
    MY_LIST_MOVIE("");

    override fun toString(): String {
        return strQuery
    }
}