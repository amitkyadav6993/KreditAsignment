package com.amityadav.kreditbeeandroidsample.utils.networkImage

import android.app.Application
import android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW
import android.graphics.Bitmap
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.DraweeConfig
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.core.ImageTranscoderType
import com.facebook.imagepipeline.core.MemoryChunkType
import com.facebook.imagepipeline.listener.RequestLoggingListener

@SuppressWarnings("MagicNumber")
object FrescoInitializer {
    fun initialize(application: Application, debugLogging: Boolean = false) {
        Fresco.initialize(
            application,
            ImagePipelineConfig.newBuilder(application)
                .setMemoryChunkType(MemoryChunkType.NATIVE_MEMORY)
                .setImageTranscoderType(ImageTranscoderType.NATIVE_TRANSCODER)
                .setBitmapMemoryCacheParamsSupplier {
                    val maxMemoryCacheSize = (Runtime.getRuntime().maxMemory() / 4).toInt()
                    MemoryCacheParams(
                        maxMemoryCacheSize,
                        Int.MAX_VALUE,
                        maxMemoryCacheSize,
                        Int.MAX_VALUE,
                        Int.MAX_VALUE
                    )
                }
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setDiskCacheEnabled(true)
                .setMainDiskCacheConfig(
                    DiskCacheConfig.newBuilder(application)
                        .setMaxCacheSize(100L * ByteConstants.MB)
                        .setMaxCacheSizeOnLowDiskSpace(40L * ByteConstants.MB)
                        .setMaxCacheSizeOnVeryLowDiskSpace(10L * ByteConstants.MB)
                        .build()
                ).setSmallImageDiskCacheConfig(
                    DiskCacheConfig.newBuilder(application)
                        .setMaxCacheSize(50L * ByteConstants.MB)
                        .setMaxCacheSizeOnLowDiskSpace(20L * ByteConstants.MB)
                        .setMaxCacheSizeOnVeryLowDiskSpace(5L * ByteConstants.MB)
                        .build()
                ).apply {
                    if (debugLogging) {
                        setRequestListeners(setOf(RequestLoggingListener()))
                    }
                }.build(),
            DraweeConfig.newBuilder()
                .build(),
            true
        )
    }

    fun onTrimMemory(level: Int) {
        // https://developer.android.com/reference/android/content/ComponentCallbacks2#constants_1
        if (level >= TRIM_MEMORY_RUNNING_LOW) {
            Fresco.getImagePipeline().clearMemoryCaches()
        }
    }
}
