package com.amityadav.kreditbeeandroidsample.presentation.base

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.SparseArray
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.amityadav.kreditbeeandroidsample.presentation.base.BaseActivity
import com.amityadav.kreditbeeandroidsample.presentation.livedata.LiveObject
import com.amityadav.kreditbeeandroidsample.presentation.livedata.call
import com.amityadav.kreditbeeandroidsample.utils.extension.ctx
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class PermissionAndStartActivityViewModel : ViewModel() {
    private val nextRequestCode by lazy { AtomicInteger(1) }
    private val resultListeners by lazy { SparseArray<(resultCode: Int, data: Intent?) -> Unit>() }
    private val permissionResultListeners by lazy { SparseArray<PermissionResultListener>() }
    @Suppress("DEPRECATION")
    internal val eventPerformWithActivity by lazy { LiveObject<Array<Event<(BaseActivity) -> Unit>>>() }

    fun startActivityForResult(
        intent: Intent,
        onResult: (resultCode: Int, data: Intent?) -> Unit
    ) {
        nextRequestCode.getAndIncrement()
        val requestCode = nextRequestCode.getAndIncrement()
        resultListeners.put(requestCode, onResult)
        performWithActivity {
            try {
                it.startActivityForResult(intent, requestCode)
            } catch (e: IllegalStateException) {
            } catch (e: ActivityNotFoundException) {
                //toast(R.string.toast_no_activity)
            }
        }
    }

    private fun performWithActivity(action: (BaseActivity) -> Unit) {
        val currArray = (eventPerformWithActivity.value ?: emptyArray())
            .filter { event ->
                !event.handled
            }.toTypedArray()

        //this can be used several times
        val nextArray = arrayOf(*currArray, Event(action))
        eventPerformWithActivity.call(nextArray)
    }

    internal fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        resultListeners[requestCode]?.invoke(resultCode, data)
        resultListeners.remove(requestCode)
    }

    @SuppressLint("ObsoleteSdkInt")//this can be used on different minimum sdk
    fun requestPermissions(
        permissions: Array<String>,
        listener: PermissionResultListener
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            listener.onPermissionGranted()
            return
        }

        if (permissions.isEmpty()) {
            listener.onPermissionGranted()
            return
        }

        val requestCode = nextRequestCode.getAndIncrement()
        permissionResultListeners.put(requestCode, listener)

        //this is called on activity because using requestCode of activity
        performWithActivity { activity ->
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        }
    }

    internal fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        performWithActivity { activity ->
            val deniedPermissions = ArrayList<String>()
            var hasPermanentDenied = false
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    continue
                }

                deniedPermissions.add(permissions[i])
                val shouldShow =
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])
                if (!shouldShow) {
                    hasPermanentDenied = true
                }
            }

            val permissionResultListener =
                permissionResultListeners[requestCode] ?: return@performWithActivity
            permissionResultListeners.remove(requestCode)

            when {
                deniedPermissions.isEmpty() -> try {
                    permissionResultListener.onPermissionGranted()
                } catch (ex: SecurityException) {
                    permissionResultListener.onPermissionException()
                }

                hasPermanentDenied -> permissionResultListener.onPermissionDeniedPermanently(
                    deniedPermissions.toTypedArray()
                )
                else -> permissionResultListener.onPermissionDenied(deniedPermissions.toTypedArray())
            }
        }
    }

    fun startPermissionSettingsPage(listener: () -> Unit) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:" + ctx.packageName)
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        startActivityForResult(intent) { _, _ ->
            listener()
        }
    }

}

/**
 * the reason to use Event instead of SingleLiveEvent is that. SingleLiveEvent is class and difficult to integrate with other livedata
 */
internal class Event<out T>(private val content: T) {

    var handled = false
        private set // Allow external read but not write

    fun handle(): T {
        handled = true
        return content
    }

    /**
     * Returns the content, even if it's already been handled.
     * this is used when one time or multi time both are used.
     */
    fun get(): T = content
}

