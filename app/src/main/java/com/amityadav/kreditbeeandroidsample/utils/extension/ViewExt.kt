package com.amityadav.kreditbeeandroidsample.utils.extension

import android.view.View
import android.widget.Toast
import com.amityadav.kreditbeeandroidsample.R
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(snackbarText: String, timeLength: Int): Snackbar =
    Snackbar.make(this, snackbarText, timeLength).also { it.show() }

fun View.showSnackbar(snackbarText: String, timeLength: Int, retry: () -> Unit): Snackbar =
    Snackbar.make(this, snackbarText, timeLength).setAction(R.string.retry) {
        retry()
    }.also { it.show() }

fun View.showToast(text: String, timeLength: Int): Toast =
    Toast.makeText(context, text, timeLength).also { it.show() }