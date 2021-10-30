package me.murattuzel.mockever.ui.movies.mapper

import me.murattuzel.mockever.R
import me.murattuzel.mockever.data.MoviesResponse
import me.murattuzel.mockever.ui.movies.MoviesViewState
import javax.inject.Inject

class MoviesSuccessMapper @Inject constructor(
    private val uiMapper: MoviesUiMapper
) {

    fun mapToViewState(response: MoviesResponse) = MoviesViewState(
        isLoading = false,
        shouldShowError = false,
        errorMessage = R.string.empty_string,
        movies = response.movies.map { uiMapper.mapToUiModel(it) }
    )
}
