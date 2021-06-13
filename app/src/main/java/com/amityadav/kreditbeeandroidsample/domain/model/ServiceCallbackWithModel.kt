package com.amityadav.kreditbeeandroidsample.domain.model

import retrofit2.Response

interface ServiceCallbackWithModel {
    fun onSuccess(responseData: Response<Any>?)
    fun onFailed(throwable: Throwable?)
}