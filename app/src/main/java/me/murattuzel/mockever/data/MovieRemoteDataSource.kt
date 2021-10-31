package me.murattuzel.mockever.data

import kotlinx.serialization.SerializationException
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val service: MovieService
) {

    suspend fun fetchMovies(shouldReturnSuccess: Boolean): MoviesResponse = invoke {
        service.fetchMovies(
            type = MockRequestType.Movies(
                shouldReturnSuccess = shouldReturnSuccess
            )
        )
    }

    suspend fun <O> invoke(serviceFunction: suspend () -> O): O {
        return try {
            serviceFunction()
        } catch (exception: Exception) {
            throw asFailure(exception)
        }
    }

    private fun asFailure(exception: Exception): Failure {
        return when (exception) {
            is Failure -> exception
            is SerializationException -> JsonError
            else -> UnknownError(exception)
        }
    }
}
