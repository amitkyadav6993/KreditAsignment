package com.amityadav.kreditbeeandroidsample.utils.networkImage.fetcher

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

class ImageFetcherCallback<T>(private val action: (T?) -> Unit, scope: CoroutineScope) : Continuation<T> {
    override val context: CoroutineContext = scope.coroutineContext

    override fun resumeWith(result: Result<T>) {
        action(result.getOrNull())
    }

}