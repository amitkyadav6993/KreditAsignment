package com.amityadav.kreditbeeandroidsample.utils.networkImage.fetcher

import android.graphics.Bitmap
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.RotationOptions
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequest.RequestLevel
import com.facebook.imagepipeline.request.ImageRequestBuilder
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FrescoImageFetcher(override val executor: Executor) : ImageFetcher {

    override suspend fun getBitmap(imageRequest: ImageRequest): Bitmap = suspendCancellableCoroutine { continuation ->
        val imagePipeline = Fresco.getImagePipeline()

        val request = ImageRequestBuilder
            .newBuilderWithSource(imageRequest.uri).apply {
                when {
                    imageRequest.useAutoRotate -> rotationOptions = RotationOptions.autoRotate()
                    imageRequest.useThumbnail -> isLocalThumbnailPreviewsEnabled = true
                    imageRequest.enableProgressiveRendering -> isProgressiveRenderingEnabled = false
                }

            }
            .setLowestPermittedRequestLevel(RequestLevel.FULL_FETCH)
            .build()

        imagePipeline.fetchDecodedImage(request, this).subscribe(object : BaseBitmapDataSubscriber() {
            override fun onNewResultImpl(bitmap: Bitmap?) {
                if (bitmap != null) {
                    continuation.resume(bitmap)
                } else {
                    continuation.resumeWithException(FetchException("Decoded bitmap is null"))
                }
            }

            override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>) {
                continuation.resumeWithException(FetchException("Decoded bitmap is broken"))
            }

        }, executor)
    }
}
