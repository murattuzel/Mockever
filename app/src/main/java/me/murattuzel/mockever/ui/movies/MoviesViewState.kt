package me.murattuzel.mockever.ui.movies

import androidx.annotation.StringRes
import me.murattuzel.mockever.R
import me.murattuzel.mockever.domain.model.MovieUiModel

data class MoviesViewState(
    val isLoading: Boolean,
    val shouldShowError: Boolean,
    @StringRes val errorMessage: Int,
    val movies: List<MovieUiModel>
) {

    companion object {
        val Initial = MoviesViewState(
            isLoading = true,
            shouldShowError = false,
            errorMessage = R.string.empty_string,
            movies = emptyList()
        )
    }
}
