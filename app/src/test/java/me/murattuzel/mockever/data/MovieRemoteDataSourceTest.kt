package me.murattuzel.mockever.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.create
import java.net.HttpURLConnection

class MovieRemoteDataSourceTest {

    lateinit var service: MovieService
    lateinit var remoteDataSource: MovieRemoteDataSource
    lateinit var mockWebServer: MockWebServer

    @ExperimentalSerializationApi
    @Before
    fun setup() {
        mockWebServer = MockWebServer().apply { start() }

        val okHttpClient = OkHttpClient.Builder()
            .build()

        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()

        service = retrofit.create()
        remoteDataSource = MovieRemoteDataSource(service)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `return movie list response when data source is requested`() = runBlocking {
        mockHttpResponse(
            "local/response_movies_success.json",
            HttpURLConnection.HTTP_OK
        )

        val response = remoteDataSource.fetchMovies(true)

        with(response) {
            assertEquals(movies.size, 10)
            assertEquals(movies[0].title, "The Shawshank Redemption")
        }
    }

    /**
     * Tried to keep it simple. Currently it is not possible to receive [Failure] object as
     * [ErrorHandlingInterceptor] is not attached to [OkHttpClient]. So, providing the error
     * json file is not necessary in this case.
     * */
    @Test
    fun `return error response when data source is requested`() = runBlocking {
        mockHttpResponse(
            "local/response_movies_error.json",
            HttpURLConnection.HTTP_BAD_REQUEST
        )

        val response = try {
            remoteDataSource.fetchMovies(false)
        } catch (exception: Exception) {
            exception
        }

        with(response) {
            assert(this is UnknownError)
            assertNotNull((this as UnknownError).throwable)
        }
    }

    private fun mockHttpResponse(fileName: String, responseCode: Int) =
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getMockJson(fileName))
        )

    private fun getMockJson(path: String): String {
        return javaClass.classLoader!!.getResourceAsStream(path)
            .bufferedReader()
            .readText()
    }
}
