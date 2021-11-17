package me.murattuzel.mockever.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.murattuzel.mockever.data.MockRequestType
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class MockRequestInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return with(chain.request()) {
            when (val mockRequestType = findMockTag()) {
                is MockRequestType -> createLocalResponse(
                    request = this,
                    requestType = mockRequestType
                )
                else -> chain.proceed(this)
            }
        }
    }

    private fun createLocalResponse(request: Request, requestType: MockRequestType): Response {
        return Response.Builder()
            .code(requestType.responseCodeStatus)
            .request(request)
            .protocol(Protocol.HTTP_2)
            .message(requestType.responseMessage)
            .body(
                readFromJson(requestType.filePath)
                    .toResponseBody("application/json".toMediaType())
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
