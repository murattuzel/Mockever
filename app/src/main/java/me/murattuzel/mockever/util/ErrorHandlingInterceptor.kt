package me.murattuzel.mockever.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.murattuzel.mockever.data.ApiError
import me.murattuzel.mockever.data.EmptyResponse
import me.murattuzel.mockever.data.HttpError
import me.murattuzel.mockever.data.NoConnectivityError
import me.murattuzel.mockever.data.TimeOutError
import me.murattuzel.mockever.data.UnknownHostError
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorHandlingInterceptor @Inject constructor(
    private val networkHandler: NetworkHandler
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkHandler.isConnected) {
            throw NoConnectivityError
        }

        val response = try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            throw when (e) {
                is UnknownHostException -> UnknownHostError
                is HttpException -> HttpError(e.code(), e.message())
                is SocketTimeoutException -> TimeOutError(e.message)
                else -> IOException(e)
            }
        }

        if (response.isSuccessful) {
            if (response.body == null) {
                throw EmptyResponse
            }
            return response
        } else {
            val responseJson = response.body?.string()
                ?: throw ApiError(
                    code = response.code,
                    message = response.message
                )
            val apiError = Json.decodeFromString<ApiErrorResponse?>(responseJson)

            throw ApiError(
                code = apiError?.statusCode ?: response.code,
                message = apiError?.message ?: UNKNOWN_ERROR
            )
        }
    }

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}

@Serializable
private data class ApiErrorResponse(
    @SerialName("status") val statusCode: Int,
    @SerialName("message") val message: String
)
