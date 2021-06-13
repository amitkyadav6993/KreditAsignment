package com.amityadav.kreditbeeandroidsample.utils.networkImage.fetcher

import android.net.Uri

data class ImageRequest @JvmOverloads constructor(
    val uri: Uri,
    val useAutoRotate: Boolean = false,
    val enableProgressiveRendering: Boolean = false,
    val useThumbnail: Boolean = false,
)
