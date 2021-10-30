package me.murattuzel.mockever.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val isConnected: Boolean
        get() = context.isNetworkAvailable()
}
