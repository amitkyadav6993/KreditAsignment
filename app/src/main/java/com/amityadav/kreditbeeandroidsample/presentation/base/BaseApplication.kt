package com.amityadav.kreditbeeandroidsample.presentation.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import com.amityadav.kreditbeeandroidsample.BuildConfig
import com.amityadav.kreditbeeandroidsample.presentation.livedata.LiveObject
import com.amityadav.kreditbeeandroidsample.utils.networkImage.FrescoInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

open class BaseApplication : Application(), LifecycleObserver {
    companion object {
        @JvmStatic
        lateinit var instance: BaseApplication
            private set
    }
    var context: Context? = null
    val name: String by lazy {
        packageManager.getApplicationLabel(applicationInfo).toString()
    }

    val defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
    val installTime: Long get() = packageManager.getPackageInfo(packageName, 0).firstInstallTime
    val processLifecycle = LiveObject<Lifecycle.Event>()

    @Suppress("RedundantModalityModifier")
    final override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)
        FrescoInitializer.initialize(this, BuildConfig.DEBUG)
        initExceptionHandler()

        initKoin(getKoinModules())
        initLifecycle()
        try {
           /* val licensePath = "assets:/5735-205-3a92fc27ec5e317be4608c16ff664f10.lic"
            // String licensePath = "assets:/SenseAR_Effects_20200319";
            NvsStreamingContext.init(
                mContext,
                licensePath,
                NvsStreamingContext.STREAMING_CONTEXT_FLAG_SUPPORT_4K_EDIT
            )
            NvAssetManager.init(mContext)*/
        }
        catch (err: UnsatisfiedLinkError){

        }
        catch (err: Exception){

        }

        onCreated()

        // Normal app init code...
    }

    private fun initLifecycle() {
        //you can use Application.ActivityLifecycleCallbacks if handling different way by activities
        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(this)


    }

    private fun initExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
           // log(e)
            defaultUncaughtExceptionHandler.uncaughtException(t, e)
        }
    }

    override fun onTerminate() {
        super.onTerminate()

        terminateKoin()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    open fun onCreated(){}

    open fun getKoinModules(): List<Module> = emptyList()

    fun initKoin(koinModules: List<Module>) {
        if (koinModules.isEmpty()) return

        startKoin {
            // use Koin logger
            androidLogger()
            androidContext(instance)
            // declare used modules
            koin.loadModules(koinModules)
        }
    }

    fun terminateKoin() {
        if (getKoinModules().isEmpty()) return

        stopKoin()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        processLifecycle.value = Lifecycle.Event.ON_START
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        processLifecycle.value = Lifecycle.Event.ON_STOP
    }

    fun isForeground(): Boolean {
        return processLifecycle.value == Lifecycle.Event.ON_START
    }

}