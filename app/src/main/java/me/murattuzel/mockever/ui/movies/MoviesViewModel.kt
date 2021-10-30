package me.murattuzel.mockever.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import me.murattuzel.mockever.data.MoviesResponse
import me.murattuzel.mockever.domain.usecase.FetchMoviesUseCase
import me.murattuzel.mockever.ui.movies.mapper.MoviesViewStateMapper
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val fetchMoviesUseCase: FetchMoviesUseCase,
    private val viewStateMapper: MoviesViewStateMapper
) : ViewModel() {
    val state: StateFlow<MoviesViewState> = flow {
        delay(1_000)
        fetchMoviesUseCase()
            .onSuccess { response -> emit(emitSuccessState(response)) }
            .onFailure { throwable -> emit(emitFailureState(throwable)) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MoviesViewState.Initial
    )

    private fun emitSuccessState(response: MoviesResponse): MoviesViewState =
        viewStateMapper.successMapper.mapToViewState(response)

    private fun emitFailureState(throwable: Throwable): MoviesViewState =
        viewStateMapper.failureMapper.mapToViewState(throwable)
}
