package com.amityadav.kreditbeeandroidsample.domain.model.api

class ViewModelResponse(val status: Status, val data: Any?, val error: Throwable?) {

    companion object {

        fun loading(): ViewModelResponse {
            return ViewModelResponse(Status.LOADING, null, null)
        }

        fun success(data: Any): ViewModelResponse {
            return ViewModelResponse(Status.SUCCESS, data, null)
        }

        fun error(error: Throwable): ViewModelResponse {
            return ViewModelResponse(Status.ERROR, null, error)
        }

        fun defaultError(error: String): ViewModelResponse {
            return ViewModelResponse(Status.DEFAULT_ERROR, error, null)
        }

        fun userNotExist(error: String): ViewModelResponse {
            return ViewModelResponse(Status.USERNOTEXIST, error, null)
        }

        fun networkError(): ViewModelResponse {
            return ViewModelResponse(Status.NETWORK_ERROR, null, null)
        }

        fun networkError(data: Any): ViewModelResponse {
            return ViewModelResponse(Status.NETWORK_ERROR, data, null)
        }


    }
}