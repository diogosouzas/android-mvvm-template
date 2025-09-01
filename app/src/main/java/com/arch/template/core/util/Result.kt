package com.arch.template.core.util

/**
 * A generic class that holds a value or an exception
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

/**
 * Returns `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null

/**
 * Returns data if [Result] is of type [Success] & holds non-null [Success.data], null otherwise.
 */
fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}

/**
 * Returns data if [Result] is of type [Success] & holds non-null [Success.data], null otherwise.
 */
val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data

/**
 * Returns exception if [Result] is of type [Error], null otherwise.
 */
val Result<*>.errorMessage: String?
    get() = (this as? Result.Error)?.exception?.message

/**
 * Updates the data in a [Result.Success] and returns the original error if it's a [Result.Error].
 */
inline fun <reified T, reified R> Result<T>.map(
    transform: (value: T) -> R,
): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> Result.Error(exception)
    is Result.Loading -> Result.Loading
}
