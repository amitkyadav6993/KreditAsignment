package com.amityadav.kreditbeeandroidsample.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class PhotoItem(

    @Json(name = "id")
    var id: String? = null,

    @Json(name = "title")
    var title: String? = null,

    @Json(name = "url")
    var url: String? = null,

    @Json(name = "thumbnailUrl")
    var thumbnailUrl: String? = null
)