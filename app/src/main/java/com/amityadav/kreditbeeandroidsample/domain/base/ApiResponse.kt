package com.amityadav.kreditbeeandroidsample.domain.base

import com.amityadav.kreditbeeandroidsample.domain.model.api.ApiError


interface ApiResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}

