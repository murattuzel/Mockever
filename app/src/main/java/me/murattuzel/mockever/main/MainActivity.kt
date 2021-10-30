package me.murattuzel.mockever.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import dagger.hilt.android.AndroidEntryPoint
import me.murattuzel.mockever.ui.movies.MoviesScreen
import me.murattuzel.mockever.ui.theme.MockeverTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MockeverTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MoviesScreen()
                }
            }
        }
    }
}
