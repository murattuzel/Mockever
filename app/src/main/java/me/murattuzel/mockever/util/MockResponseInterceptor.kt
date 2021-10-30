package me.murattuzel.mockever.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.murattuzel.mockever.data.MockRequestType
import me.murattuzel.mockever.data.MovieService
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class MockResponseInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return with(chain.request()) {
            when (findMockTag()) {
                MockRequestType.MOVIES -> createLocalResponse(
                    request = this,
                    filePath = MovieService.MOVIES
                )
                else -> chain.proceed(this)
            }
        }
    }

    private fun createLocalResponse(request: Request, filePath: String): Response {
        return Response.Builder()
            .code(200)
            .request(request)
            .protocol(Protocol.HTTP_2)
            .message("OK")
            .body(
                readFromJson(filePath).toResponseBody("application/json".toMediaType())
            )
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun readFromJson(filePath: String): ByteArray {
        return context.resources
            .assets
            .open(filePath)
            .readBytes()
    }
}
