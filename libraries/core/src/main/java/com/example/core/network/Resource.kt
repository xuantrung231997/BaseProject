package com.example.core.network

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Resource<out T : Any> {

    object Loading : Resource<Nothing>()
    data class Success<out T : Any>(val data: T) : Resource<T>()
    object NoData : Resource<Nothing>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is Success<*> -> "Success[data=$data]"
            is NoData -> "NoData"
            is Error -> "Error[throwable=$throwable]"
        }
    }
}


