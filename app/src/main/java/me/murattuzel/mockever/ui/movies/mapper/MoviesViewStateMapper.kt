package me.murattuzel.mockever.ui.movies.mapper

import javax.inject.Inject

class MoviesViewStateMapper @Inject constructor(
    val successMapper: MoviesSuccessMapper,
    val failureMapper: MoviesFailureMapper
)
