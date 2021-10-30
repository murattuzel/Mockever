package me.murattuzel.mockever.ui.movies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import me.murattuzel.mockever.R
import me.murattuzel.mockever.domain.model.MovieUiModel
import me.murattuzel.mockever.util.rememberFlowWithLifecycle

@Composable
fun MoviesScreen() {
    val viewModel = hiltViewModel<MoviesViewModel>()
    val viewState by rememberFlowWithLifecycle(viewModel.state)
        .collectAsState(initial = MoviesViewState.Initial)
    MoviesScreen(viewState = viewState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MoviesScreen(viewState: MoviesViewState) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Movies") }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        when {
            viewState.isLoading -> LoadingContent()
            viewState.shouldShowError -> ErrorContent()
            viewState.movies.isNotEmpty() -> {
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize()
                ) {
                    moviesGrid(movies = viewState.movies)
                }
            }
        }
    }
}

@Composable
fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { CircularProgressIndicator() }
}

@Composable
fun ErrorContent() {
    Text(text = "Error")
}

// @OptIn(ExperimentalCoilApi::class)
@Composable
fun MovieListItem(
    modifier: Modifier = Modifier,
    movie: MovieUiModel
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberImagePainter(
                    data = movie.image,
                    builder = { placeholder(R.drawable.ic_launcher_background) }
                ),
                contentDescription = "poster",
                contentScale = ContentScale.FillBounds
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            )
                        )
                        onDrawWithContent {
                            drawRect(gradient)
                            drawContent()
                        }
                    }
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(8.dp),
                text = movie.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun LazyListScope.moviesGrid(movies: List<MovieUiModel>) {
    items(movies.windowed(2, 2, true)) { rowItem ->
        Row(modifier = Modifier.fillMaxWidth()) {
            rowItem.forEach { movie ->
                MovieListItem(
                    modifier = Modifier
                        .height(290.dp)
                        .fillParentMaxWidth(0.5f)
                        .padding(8.dp),
                    movie = movie
                )
            }
        }
    }
}

@Preview
@Composable
fun MoviesScreenPreview() {
    MoviesScreen(
        viewState = MoviesViewState(
            isLoading = false,
            shouldShowError = false,
            errorMessage = R.string.empty_string,
            movies = mutableListOf<MovieUiModel>().apply {
                repeat(10) {
                    add(
                        MovieUiModel(
                            title = "The Lord of the Rings: The Return of the King",
                            overview = "overview",
                            image = "url"
                        )
                    )
                }
            }
        )
    )
}
