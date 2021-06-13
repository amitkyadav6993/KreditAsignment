package com.amityadav.kreditbeeandroidsample.utils.extension

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.IntDef
import com.amityadav.kreditbeeandroidsample.BuildConfig
import com.amityadav.kreditbeeandroidsample.presentation.base.BaseApplication
import org.koin.core.context.KoinContextHandler


val isDebug: Boolean
    inline get() = BuildConfig.DEBUG

val isProdRelease: Boolean
    inline get() = !isDebug

val isTesting = noThrow { Class.forName("androidx.test.espresso.Espresso") } != null
        || noThrow { Class.forName("org.robolectric.RobolectricTestRunner") } != null

val ctx: Context
    inline get() = app

val app: BaseApplication
    inline get() = BaseApplication.instance

val handler by lazy { Handler(Looper.getMainLooper()) }

val androidId by lazy {
    getAndroidId()
}

@SuppressLint("HardwareIds")
private fun getAndroidId() {
    Settings.Secure.getString(ctx.contentResolver, Settings.Secure.ANDROID_ID)
}

@Suppress("DEPRECATION")
val imei: String?
    @SuppressLint("HardwareIds", "MissingPermission")
    get() = ""

fun toast(text: String?, duration: Int) {
    handler.post { Toast.makeText(ctx, text, duration).show() }
}

fun toast(text: String?) {
    toast(text, Toast.LENGTH_SHORT)
}

fun toast(textResId: Int) {
    toast(ctx.getString(textResId))
}

fun toastLong(text: String?) {
    toast(text, Toast.LENGTH_LONG)
}

fun toastLong(textResId: Int) {
    toast(ctx.getString(textResId), Toast.LENGTH_LONG)
}


inline fun <T> noThrow(action: () -> T): T? {
    return try {
        action()
    } catch (e: Exception) {
        null
    }
}


@IntDef(value = [Build.VERSION_CODES.N, Build.VERSION_CODES.M, Build.VERSION_CODES.LOLLIPOP], flag = true)
@Retention(AnnotationRetention.SOURCE)
annotation class VersionParam

fun isFromVersion(@VersionParam version: Int): Boolean = Build.VERSION.SDK_INT >= version

val koin get() = KoinContextHandler.get()
