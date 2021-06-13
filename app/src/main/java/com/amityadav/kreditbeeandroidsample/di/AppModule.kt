package com.amityadav.kreditbeeandroidsample.di

import com.amityadav.kreditbeeandroidsample.presentation.album.viewmodel.AlbumViewModel
import com.amityadav.kreditbeeandroidsample.presentation.album.viewmodel.PhotoViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    viewModel { AlbumViewModel(get(), get()) }

    viewModel { PhotoViewModel(get(), get()) }

    single { createNetworkManager(get()) }

    single { providePreferenceImplementer(get()) }

    single { providePreferenceInstance(androidApplication()) }

}