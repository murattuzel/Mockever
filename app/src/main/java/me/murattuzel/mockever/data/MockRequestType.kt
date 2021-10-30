package me.murattuzel.mockever.data

enum class MockRequestType(val endpoint: String) {
    MOVIES(MovieService.MOVIES)
}
