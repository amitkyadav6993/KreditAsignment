package com.amityadav.kreditbeeandroidsample.utils.networkImage.fetcher

import android.graphics.Bitmap
import java.util.concurrent.Executor

interface ImageFetcher {

    val executor: Executor

    suspend fun getBitmap(imageRequest: ImageRequest): Bitmap
}
