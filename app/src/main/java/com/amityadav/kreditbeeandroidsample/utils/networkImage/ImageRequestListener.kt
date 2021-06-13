package com.amityadav.kreditbeeandroidsample.utils.networkImage

import android.graphics.drawable.Animatable
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.imagepipeline.image.ImageInfo

abstract class ImageRequestListener : BaseControllerListener<ImageInfo>() {
    final override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) =
        onFinalImageSet()

    abstract fun onFinalImageSet()
}