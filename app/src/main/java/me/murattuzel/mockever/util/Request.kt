package me.murattuzel.mockever.util

import me.murattuzel.mockever.data.MockRequestType
import okhttp3.Request

/**
 * Returns [MockRequestType] tag if attached, otherwise returns null
 * */
fun Request.findMockTag(): MockRequestType? = tag(MockRequestType::class.java)
