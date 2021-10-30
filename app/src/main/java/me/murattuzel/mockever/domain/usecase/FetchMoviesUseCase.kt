package me.murattuzel.mockever.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.murattuzel.mockever.data.MovieRemoteDataSource
import me.murattuzel.mockever.data.MoviesResponse
import java.io.IOException
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(
    private val dataSource: MovieRemoteDataSource
) {

    suspend operator fun invoke(): Result<MoviesResponse> = withContext(Dispatchers.IO) {
        try {
            Result.success(dataSource.fetchMovies())
        } catch (exception: IOException) {
            Result.failure(exception)
        }
    }
}
