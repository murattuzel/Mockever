package me.murattuzel.mockever.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

val Context.connectivityManager
    get() = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

fun Context.isNetworkAvailable(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        connectivityManager.activeNetwork
        connectivityManager.apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    } else {
        @Suppress("DEPRECATION")
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}
