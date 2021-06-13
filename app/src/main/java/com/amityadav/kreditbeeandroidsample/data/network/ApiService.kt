package com.amityadav.kreditbeeandroidsample.data.network

import com.amityadav.kreditbeeandroidsample.domain.model.AlbumItem
import com.amityadav.kreditbeeandroidsample.domain.model.PhotoItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("albums")
    suspend fun getAlbums(@Query("albumId") params: Any?): List<AlbumItem>

    @GET("photos")
    suspend fun getPhotoById(@Query("albumId") id: Any?, @Query("limit") limit: Any?, @Query("page") page: Any?) : List<PhotoItem>

}