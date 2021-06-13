package com.amityadav.kreditbeeandroidsample.data.network

import com.amityadav.kreditbeeandroidsample.domain.base.ApiResponse
import com.amityadav.kreditbeeandroidsample.domain.exception.traceErrorException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

abstract class NetworkManager<Type, in Params>() where Type : Any {

    abstract suspend fun runGetAlbums(params: Params? = null): Type

    abstract suspend fun runGetPhotosById(id: Params?, limit: Params, offset: Params): Type

    fun getAlbums(scope : CoroutineScope, params : Params?, onResult: ApiResponse<Any>?){
        scope.launch {
            try {
                val result = runGetAlbums(params)
                onResult?.onSuccess(result)
            }catch (e: CancellationException) {
                e.printStackTrace()
                onResult?.onError(traceErrorException(e))
            } catch (e: Exception) {
                e.printStackTrace()
                onResult?.onError(traceErrorException(e))
            }

        }
    }

    fun getPhotosById(scope: CoroutineScope, id: Params?, limit: Params, offset: Params, onResult: ApiResponse<Any>?){
        scope.launch {
            try {
                val result = runGetPhotosById(id, limit, offset)
                onResult?.onSuccess(result)
            }catch (e: CancellationException) {
                e.printStackTrace()
                onResult?.onError(traceErrorException(e))
            } catch (e: Exception) {
                e.printStackTrace()
                onResult?.onError(traceErrorException(e))
            }

        }
    }

}