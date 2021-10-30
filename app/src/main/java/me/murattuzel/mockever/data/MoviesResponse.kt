package me.murattuzel.mockever.data

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class MoviesResponse(
    val movies: List<MovieResponse>
)

@Serializable
data class MovieResponse(
    val title: String,
    val overview: String,
    val image: String
)
