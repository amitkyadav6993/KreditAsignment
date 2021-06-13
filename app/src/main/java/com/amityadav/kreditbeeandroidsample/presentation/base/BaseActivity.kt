package com.amityadav.kreditbeeandroidsample.presentation.base

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.MenuRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.amityadav.kreditbeeandroidsample.presentation.base.*
import com.amityadav.kreditbeeandroidsample.R
import com.amityadav.kreditbeeandroidsample.presentation.livedata.LiveObject
import com.amityadav.kreditbeeandroidsample.presentation.livedata.State
import com.amityadav.kreditbeeandroidsample.utils.extension.createProgressDialog
import com.amityadav.kreditbeeandroidsample.utils.extension.dismissWithoutException
import com.amityadav.kreditbeeandroidsample.utils.extension.showWithoutException

abstract class BaseActivity : AppCompatActivity(), IBaseUi {

    open val navHostId: Int = 0
    override val navController: NavController
        get() {
            Log.d("navbarcontroller",""+true)
            require(navHostId != 0) { "navHostId is not set" }
            return findNavController(navHostId)
        }

    override val toolbarId: Int get() = R.id.toolbar

    override val appBarConfiguration: AppBarConfiguration?
        get() = if (navHostId != 0) AppBarConfiguration(navController.graph) else null


    override val progressDialog by lazy { createProgressDialog() }

    @MenuRes
    private var menuId: Int = 0
    private lateinit var onMenuItemClickListener: (MenuItem) -> Boolean


    override lateinit var binding: ViewDataBinding
    override val viewModels = mutableListOf<Pair<Int, Lazy<BaseViewModel>>>()

    val permissionStartActivityViewModel by viewModels<PermissionAndStartActivityViewModel>()

    override val stateObserver: Observer<State> by lazy { resourceObserverCommon() }
    override val initStateObserver: Observer<State> by lazy { resourceObserverInit() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setupContentWindow()
        //log("${this::class.simpleName} onCreate")

        setupView()
        setupActionbar()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupObserver()
    }

    override fun onViewModelSetup() {
    }

    override fun onStart() {
        super.onStart()
        viewModels.map { it.second.value }.forEach {
            it.onStart()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModels.map { it.second.value }.forEach {
            it.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModels.map { it.second.value }.forEach {
            it.onPause()
        }
    }

    override fun onStop() {
        super.onStop()
        viewModels.map { it.second.value }.forEach {
            it.onStop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //log("${this::class.simpleName} onDestroy")
    }

    private fun setupView() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModels.forEach { (variableId, viewModel) ->
            binding.setVariable(variableId, viewModel.value)
        }
        binding.lifecycleOwner = this
    }

    private fun setupObserver() {
        permissionStartActivityViewModel.eventPerformWithActivity.observe { array ->
            array.forEach { event ->
                if (!event.handled) {
                    event.handle()(this)
                }
            }
        }

        viewModels.map { it.second.value }.forEach {
            it.state.observe(stateObserver)
            it.initState.observe(initStateObserver)

            it.eventSnackbarByString.observeEvent {
                showSnackbar(it)
            }

            it.eventSnackbarById.observeEvent {
                showSnackbar(getString(it))
            }

            it.eventStartActivity.observeEvent {
                startActivity(it)
            }

            it.eventShowProgressBar.observeEvent {
                if (it) {
                    progressDialog.showWithoutException()
                } else {
                    progressDialog.dismissWithoutException()
                }
            }

            it.eventNav.observeEvent { action ->
                action(navController)
            }
            it.eventStartActivityForResult.observeEvent {
                permissionStartActivityViewModel.startActivityForResult(it.intent, it.onResult)
            }
            it.eventRequestPermission.observeEvent {
                permissionStartActivityViewModel.requestPermissions(it.permissions, it.listener)
            }
            it.eventPermissionSettingPage.observeEvent {
                permissionStartActivityViewModel.startPermissionSettingsPage(it)
            }
        }

        onViewModelSetup()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        permissionStartActivityViewModel.onActivityResult(requestCode, resultCode, data)
    }

    @CallSuper
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionStartActivityViewModel.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }

    private fun setupActionbar() {
        val toolbar = findViewById<View>(toolbarId)
        if (toolbar == null || toolbar !is Toolbar) {
            return
        }

        setSupportActionBar(toolbar)

        appBarConfiguration?.let {
            //the title in the action bar will automatically be updated when the destination changes
            // [AppBarConfiguration] you provide controls how the Navigation button is displayed.
            setupActionBarWithNavController(navController, it)
        }
    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        //handle on up button is pressed.
//        return appBarConfiguration?.let {
//            navController.navigateUp()
//        }?:super.onSupportNavigateUp()
//    }
//
//    override fun setMenu(@MenuRes menuId: Int, onMenuItemClickListener: (MenuItem) -> Boolean) {
//        this.menuId = menuId
//        this.onMenuItemClickListener = onMenuItemClickListener
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        if (menuId == 0) {
//            return super.onCreateOptionsMenu(menu)
//        }
//
//        menuInflater.inflate(menuId, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (!::onMenuItemClickListener.isInitialized) {
//            return super.onOptionsItemSelected(item)
//        }
//
//        //if menu id and nav's fragment id is same, then redirect
//        if (navHostId != 0 && item.onNavDestinationSelected(navController)) {
//            return true
//        }
//
//        if (onMenuItemClickListener(item)) {
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun navigate(id: Int) {
//        navController.navigate(id)
//    }
//
//    override fun NavDirections.navigate() {
//        navController.navigate(this)
//    }

    override fun <T> LiveObject<T>.observe(onChanged: (T) -> Unit) {
        observe(this@BaseActivity, onChanged)
    }

    override fun <T> LiveObject<T>.observeEvent(onChanged: (T) -> Unit) {
        observeEvent(this@BaseActivity, onChanged)
    }

    override fun <T> LiveObject<T>.observeEvent(observer: Observer<in T>) {
        observeEvent(this@BaseActivity, observer)
    }

    override fun <T> LiveObject<T>.observe(observer: Observer<in T>) {
        observe(this@BaseActivity, observer)
    }


    fun setupContentWindow() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    fun changeStatusBarColor(@ColorRes barColor: Int) {
        window.apply {
            statusBarColor = getColorFromRes(barColor)
            //navigationBarColor = getColorFromRes(barColor)
        }
    }

    private fun getColorFromRes(barColor: Int): Int {
        return ContextCompat.getColor(this, barColor)
    }

}
