package me.murattuzel.mockever.util

import androidx.annotation.StringRes
import me.murattuzel.mockever.R
import me.murattuzel.mockever.data.HttpError
import me.murattuzel.mockever.data.NoConnectivityError

@StringRes
fun Throwable.errorStringResource(): Int = when (this) {
    is NoConnectivityError -> R.string.no_internet_connection
    is HttpError -> R.string.server_error_message
    else -> R.string.unknown_error_message
}
