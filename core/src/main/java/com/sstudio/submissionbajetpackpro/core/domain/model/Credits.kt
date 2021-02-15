package com.sstudio.submissionbajetpackpro.core.domain.model

data class Credits (
    val id: Int = 0,
    val cast: List<Cast> = listOf(),
    val crew: List<Crew> = listOf()
) {
    data class Cast(
        val adult: Boolean,
        val gender: Int,
        val id: Int,
        val knownForDepartment: String = "",
        val name: String = "",
        val originalName: String = "",
        val popularity: Double = 0.0,
        val profilePath: String = "",
        val castId: Int = 0,
        val character: String = "",
        val creditId: String = "",
        val order: Int = 0
    )

    data class Crew(
        val adult: Boolean,
        val gender: Int,
        val id: Int,
        val knownForDepartment: String = "",
        val name: String = "",
        val originalName: String = "",
        val popularity: Double = 0.0,
        val profilePath: String = "",
        val creditId: String = "",
        val department: String = "",
        val job: String = ""
    )
}