package com.example.newsapp.helper.extensions.fragment

import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newsapp.helper.extensions.activity.hideSoftKeyboard
import com.example.newsapp.helper.extensions.activity.setupActionBar
import com.example.newsapp.helper.extensions.activity.showSoftKeyboard
import com.example.newsapp.helper.extensions.context.makeToast

fun Fragment.setupActionBar(
    @IdRes toolbarId: Int,
    @DrawableRes backIcon: Int? = null,
    action: ActionBar.() -> Unit = {}
): Toolbar? = activity?.setupActionBar(toolbarId, backIcon, action)

fun Fragment.showSoftKeyboard(
    view: View
) {
    activity?.showSoftKeyboard(view)
}

fun Fragment.hideSoftKeyboard() {
    activity?.hideSoftKeyboard()
}

fun Fragment.makeToast(
    @StringRes messageRes: Int,
    duration: Int = Toast.LENGTH_LONG
) {
    context?.makeToast(getString(messageRes), duration)
}

fun Fragment.makeToast(
    message: String,
    duration: Int = Toast.LENGTH_LONG
) {
    context?.makeToast(message, duration)
}

fun <T> Fragment.getNavigationResult(key: String) =
    findNavController().currentBackStackEntry?.savedStateHandle?.get<T>(key)

fun <T> Fragment.getNavigationResultLiveData(key: String) =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

