package me.murattuzel.mockever.data

import java.io.IOException

sealed class Failure : IOException()

data class ApiError(
    val code: Int,
    override val message: String,
    val errorCode: String? = null
) : Failure()

data class UnknownError(
    val throwable: Throwable
) : Failure()

data class HttpError(
    val code: Int,
    override val message: String
) : Failure()

data class TimeOutError(
    override val message: String?
) : Failure()

object JsonError : Failure()
object UnknownHostError : Failure()
object NoConnectivityError : Failure()
object EmptyResponse : Failure()
