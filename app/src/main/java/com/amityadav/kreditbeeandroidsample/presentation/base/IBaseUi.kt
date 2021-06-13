package com.amityadav.kreditbeeandroidsample.presentation.base

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import com.amityadav.kreditbeeandroidsample.R
import com.amityadav.kreditbeeandroidsample.presentation.livedata.LiveObject
import com.amityadav.kreditbeeandroidsample.presentation.livedata.State
import com.amityadav.kreditbeeandroidsample.utils.extension.*
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.compat.ViewModelCompat.viewModel

interface IBaseUi {
    /**
     * viewModel name should be "model" for auto binding
     * if you'd like to change it, override setVariable
     */
    var binding: ViewDataBinding
    val viewModels: MutableList<Pair<Int, Lazy<BaseViewModel>>>
    val layoutId: Int

    /**
     * toolbar id is "toolbar"
     * If you want to change, override this property
     */
    val toolbarId: Int
    val appBarConfiguration: AppBarConfiguration?

//    fun setMenu(@MenuRes menuId: Int, onMenuItemClickListener: (MenuItem) -> Boolean)
//
//    fun navigate(@IdRes id: Int)
//    fun NavDirections.navigate()
    val navController: NavController

    fun showSnackbar(text: String) {
        binding.root.showSnackbar(text, Snackbar.LENGTH_SHORT)
    }

    fun showToast(text: String) {
        binding.root.showToast(text, Toast.LENGTH_SHORT)
    }

    /**
     * when observe LiveData, override this
     */
    fun onViewModelSetup()

//  fun getSavedState(savedStateRegistryOwner: SavedStateRegistryOwner = this): SavedStateHandle
//fun <reified T : NavArgs> getNavArgs(): T

    /**
     * set state observer to change loading and error on state liveData
     */
    val stateObserver: Observer<State>
    /**
     * hide layout by showing full error fragment if not success,
     */
    val initStateObserver: Observer<State>

    val progressDialog: AlertDialog

    fun <T> LiveObject<T>.observe(onChanged: (T) -> Unit)
    fun <T> LiveObject<T>.observeEvent(onChanged: (T) -> Unit)
    fun <T> LiveObject<T>.observeEvent(observer: Observer<in T>)
    fun <T> LiveObject<T>.observe(observer: Observer<in T>)
}

fun IBaseUi.resourceObserverCommon(onResult: (State) -> Boolean = { false }): Observer<State> =
    Observer {
        if (onResult(it)) {
            return@Observer
        }
        if (it.isLoading()) {
            progressDialog.showWithoutException()
        } else {
            progressDialog.dismissWithoutException()
        }

        dismissSnackbar(binding.root)// if state is changed, dismiss snackbar if it's shown.

        it.onError {
            showErrorSnackbar(binding.root, it.retry)
        }

    }

fun IBaseUi.resourceObserverInit(onResult: (State) -> Boolean = { false }): Observer<State> =
    resourceObserverCommon {
        binding.root.visibility = if (it.isSuccess()) View.VISIBLE else View.GONE
        false
    }

fun dismissSnackbar(view: View) {
    val snackbar = view.getTag(R.id.view_tag_snackbar) as? Snackbar? ?: return
    snackbar.dismiss()
    view.setTag(R.id.view_tag_snackbar, null)
}

fun showErrorSnackbar(view: View, retry: () -> Unit) {
    view.showSnackbar(ctx.getString(R.string.error_occurred), Snackbar.LENGTH_SHORT, retry).also {
        view.setTag(R.id.view_tag_snackbar, it)
    }
}
//
//fun IBaseUi.getSavedState(savedStateRegistryOwner: SavedStateRegistryOwner = this): SavedStateHandle {
//    return SavedStateViewModelFactory(
//        app,
//        savedStateRegistryOwner
//    ).create(SavedStateViewModel::class.java)
//        .savedStateHandle
//}

internal class SavedStateViewModel(val savedStateHandle: SavedStateHandle) : ViewModel()

inline fun <reified V : BaseViewModel> IBaseUi.bindingViewModel(
    variableId: Int = BR.model,
    qualifier: ViewModelStoreOwner,
    clazz: Class<V>,
): Lazy<V> {
    return viewModel<V>(qualifier, clazz).also {
        viewModels.add(Pair(variableId, it))
    }
}