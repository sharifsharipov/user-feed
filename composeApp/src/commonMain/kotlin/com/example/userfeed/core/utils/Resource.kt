package com.example.userfeed.core.utils

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure(val error: AppError) : Resource<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Failure

    fun getOrNull(): T? = when (this) {
        is Success -> data
        is Failure -> null
    }

    inline fun <R> fold(
        onSuccess: (T) -> R,
        onFailure: (AppError) -> R
    ): R = when (this) {
        is Success -> onSuccess(data)
        is Failure -> onFailure(error)
    }

    inline fun onSuccess(action: (T) -> Unit): Resource<T> {
        if (this is Success) action(data)
        return this
    }

    inline fun onFailure(action: (AppError) -> Unit): Resource<T> {
        if (this is Failure) action(error)
        return this
    }

    inline fun <R> map(transform: (T) -> R): Resource<R> = when (this) {
        is Success -> Success(transform(data))
        is Failure -> this
    }

    inline fun <R> flatMap(transform: (T) -> Resource<R>): Resource<R> = when (this) {
        is Success -> transform(data)
        is Failure -> this
    }
}

sealed class AppError(open val message: String) {
    data class NetworkError(
        override val message: String = "Internet bilan aloqa yo'q"
    ) : AppError(message)

    data class ServerError(
        override val message: String = "Server xatoligi",
        val code: Int? = null
    ) : AppError(message)

    data class DatabaseError(
        override val message: String = "Ma'lumotlar bazasi xatoligi"
    ) : AppError(message)

    data class NotFoundError(
        override val message: String = "Ma'lumot topilmadi"
    ) : AppError(message)

    data class UnknownError(
        override val message: String = "Noma'lum xatolik"
    ) : AppError(message)
}
