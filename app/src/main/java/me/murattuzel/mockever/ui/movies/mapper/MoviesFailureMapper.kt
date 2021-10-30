package me.murattuzel.mockever.ui.movies.mapper

import me.murattuzel.mockever.ui.movies.MoviesViewState
import me.murattuzel.mockever.util.errorStringResource
import javax.inject.Inject

class MoviesFailureMapper @Inject constructor() {

    fun mapToViewState(throwable: Throwable) = MoviesViewState(
        isLoading = false,
        shouldShowError = true,
        errorMessage = throwable.errorStringResource(),
        movies = emptyList()
    )
}
