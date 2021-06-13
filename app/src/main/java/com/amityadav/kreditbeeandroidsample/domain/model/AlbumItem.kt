package com.amityadav.kreditbeeandroidsample.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class AlbumItem(

    @Json(name = "id")
    var id: String? = null,

    @Json(name = "title")
    var title: String? = null
)