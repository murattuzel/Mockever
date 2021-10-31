package me.murattuzel.mockever.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.murattuzel.mockever.data.MoviesResponse
import me.murattuzel.mockever.domain.usecase.FetchMoviesUseCase
import me.murattuzel.mockever.ui.movies.mapper.MoviesViewStateMapper
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val fetchMoviesUseCase: FetchMoviesUseCase,
    private val viewStateMapper: MoviesViewStateMapper
) : ViewModel() {
    private val _state = MutableStateFlow(MoviesViewState.Initial)
    val state: StateFlow<MoviesViewState> = _state

    val onSuccessData: (() -> Unit) = { fetchData(shouldReturnSuccess = true) }
    val onFailureData: (() -> Unit) = { fetchData(shouldReturnSuccess = false) }

    init {
        fetchData(shouldReturnSuccess = true)
    }

    private fun fetchData(shouldReturnSuccess: Boolean) = viewModelScope.launch {
        _state.value = MoviesViewState.Initial
        delay(1_000)
        fetchMoviesUseCase(shouldReturnSuccess)
            .onSuccess { response ->
                _state.value = emitSuccessState(response)
            }
            .onFailure { throwable ->
                _state.value = emitFailureState(throwable)
            }
    }

    private fun emitSuccessState(response: MoviesResponse): MoviesViewState =
        viewStateMapper.successMapper.mapToViewState(response)

    private fun emitFailureState(throwable: Throwable): MoviesViewState =
        viewStateMapper.failureMapper.mapToViewState(throwable)
}
