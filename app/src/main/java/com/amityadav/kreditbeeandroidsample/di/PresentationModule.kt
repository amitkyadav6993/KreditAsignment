package com.amityadav.kreditbeeandroidsample.di

import com.amityadav.kreditbeeandroidsample.utils.networkImage.fetcher.FrescoImageFetcher
import com.amityadav.kreditbeeandroidsample.utils.networkImage.fetcher.ImageFetcher
import com.facebook.imagepipeline.core.DefaultExecutorSupplier
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationUiModule = module {
    single<ImageFetcher>(named("fresco")) { FrescoImageFetcher(DefaultExecutorSupplier(1).forBackgroundTasks()) }
}
