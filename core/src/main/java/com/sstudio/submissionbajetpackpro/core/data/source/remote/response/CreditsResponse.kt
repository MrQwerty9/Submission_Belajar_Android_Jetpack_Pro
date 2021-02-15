package com.sstudio.submissionbajetpackpro.core.data.source.remote.response

data class CreditsResponse (
    val id: Int = 0,
    val cast: List<Cast> = listOf(),
    val crew: List<Crew> = listOf()
) {
    data class Cast(
        val adult: Boolean,
        val gender: Int,
        val id: Int,
        val known_for_department: String? = "",
        val name: String? = "",
        val original_name: String? = "",
        val popularity: Double? = 0.0,
        val profile_path: String? = "",
        val cast_id: Int? = 0,
        val character: String? = "",
        val credit_id: String? = "",
        val order: Int? = 0
    )

    data class Crew(
        val adult: Boolean,
        val gender: Int,
        val id: Int,
        val known_for_department: String? = "",
        val name: String? = "",
        val original_name: String? = "",
        val popularity: Double? = 0.0,
        val profile_path: String? = "",
        val credit_id: String? = "",
        val department: String? = "",
        val job: String? = ""
    )
}