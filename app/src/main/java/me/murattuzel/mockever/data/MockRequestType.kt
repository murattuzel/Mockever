package me.murattuzel.mockever.data

sealed class MockRequestType {
    abstract val filePathSuccess: String
    abstract val filePathError: String
    abstract val shouldReturnSuccess: Boolean

    val filePath by lazy { if (shouldReturnSuccess) filePathSuccess else filePathError }
    val responseCodeStatus by lazy { if (shouldReturnSuccess) 200 else 400 }
    val responseMessage by lazy { if (shouldReturnSuccess) "OK" else "Bad Request" }

    data class Movies(
        override val filePathSuccess: String = MovieService.MOVIES,
        override val filePathError: String = MovieService.MOVIES_ERROR,
        override val shouldReturnSuccess: Boolean
    ) : MockRequestType()
}
