package com.amityadav.kreditbeeandroidsample

import android.content.res.Configuration
import com.amityadav.kreditbeeandroidsample.di.AppModule
import com.amityadav.kreditbeeandroidsample.di.NetworkModule
import com.amityadav.kreditbeeandroidsample.di.presentationUiModule
import com.amityadav.kreditbeeandroidsample.presentation.base.BaseApplication
import com.amityadav.kreditbeeandroidsample.utils.networkImage.FrescoInitializer
import org.koin.core.module.Module


class MainApplication : BaseApplication() {

    var DPI = "1.5"

    override fun onCreated() {
        super.onCreated()
        val screenSize = resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        when (screenSize) {
            Configuration.SCREENLAYOUT_SIZE_SMALL -> DPI = "1.0"
            else -> DPI = "1.5"
        }
    }

    override fun getKoinModules(): List<Module> {
        return listOf(AppModule, NetworkModule, presentationUiModule)
    }

    override fun onTrimMemory(level: Int) {
        FrescoInitializer.onTrimMemory(level)
        super.onTrimMemory(level)
    }
}