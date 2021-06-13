package com.amityadav.kreditbeeandroidsample.utils.networkImage

import android.graphics.Bitmap
import android.graphics.Canvas
import com.facebook.imagepipeline.request.BasePostprocessor

abstract class ImageOverlay {
    internal val postProcessor = OverlayPostprocessor(::onDrawOverlay)

    abstract fun onDrawOverlay(canvas: Canvas)
}

internal class OverlayPostprocessor(
    private val onDrawOverlay: (canvas: Canvas) -> Unit,
) : BasePostprocessor() {

    override fun process(bitmap: Bitmap) {
        onDrawOverlay(Canvas(bitmap))
    }
}
