package com.amityadav.kreditbeeandroidsample.presentation.livedata

import kotlinx.coroutines.Job

sealed class Resource<out T> {
    data class Loading(val job: Job? = null) : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val error: RuntimeException, val retry: () -> Unit = {}) : Resource<Nothing>() {
        init {
           // log(error)
        }
    }

    fun get(): T? = when (this) {
        is Success -> data
        else -> null
    }

    fun onLoading(onResult: (job: Job?) -> Unit): Resource<T> {
        if (this is Loading) {
            onResult(job)
        }
        return this
    }

    fun onSuccess(onResult: (T) -> Unit): Resource<T> {
        if (this is Success) {
            onResult(data)
        }
        return this
    }

    fun onError(onResult: (Error) -> Unit): Resource<T> {
        if (this is Error) {
            onResult(this)
        }
        return this
    }

    inline fun <reified E : ResourceError> onErrorOf(onResult: (E) -> Unit): Resource<T> {
        if (this is E) {
            onResult(this)
        }
        return this
    }

    fun isSuccess() = this is Success
    fun isLoading() = this is Loading
    fun isError() = this is Error
    fun isResult() = isSuccess() || isError()
    inline fun <reified E : ResourceError> isErrorOf() = this is Error && this.error is E
}


fun Resource<*>?.isLoadingState() = this?.isLoading() ?: false

fun Resource<*>?.isSuccessState() = this?.isSuccess() ?: false


fun Resource<*>?.isErrorState() = this?.isError() ?: false
fun Resource<*>?.isResultState() = this?.isResult() ?: false

fun <T> Resource<T>.asLive() = LiveResource(this)


typealias State = Resource<Any?>

class ResourceException(val error: ResourceError) : Exception()

open class ResourceError(cause: Throwable? = null) : RuntimeException(cause) {
    fun asResource(): Resource<Nothing> = Resource.Error(this)
    fun <T> asLiveResource(): LiveResource<T> = asResource().asLive()
}