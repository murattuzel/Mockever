package me.murattuzel.mockever.data

import retrofit2.http.GET
import retrofit2.http.Tag

interface MovieService {

    @GET(MOVIES)
    suspend fun fetchMovies(
        @Tag type: MockRequestType
    ): MoviesResponse

    companion object {
        const val MOVIES = "local/response_movies_success.json"
        const val MOVIES_ERROR = "local/response_movies_error.json"
    }
}
