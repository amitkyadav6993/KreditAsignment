package com.amityadav.kreditbeeandroidsample.data.network

class NetworkManagerImpl constructor(
    private val apiService: ApiService,
) : NetworkManager<Any, Any?>() {

    override suspend fun runGetAlbums(params: Any?): Any {
        return apiService.getAlbums(params)
    }

    override suspend fun runGetPhotosById(id: Any?, limit: Any?, offset: Any?): Any {
        return apiService.getPhotoById(id, limit, offset)
    }

}