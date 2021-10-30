package me.murattuzel.mockever.ui.movies.mapper

import me.murattuzel.mockever.data.MovieResponse
import me.murattuzel.mockever.domain.model.MovieUiModel
import javax.inject.Inject

class MoviesUiMapper @Inject constructor() {

    fun mapToUiModel(response: MovieResponse): MovieUiModel = with(response) {
        MovieUiModel(
            title = title,
            overview = overview,
            image = image
        )
    }
}
