package com.example.newsapp.helper.extensions.activity

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun Activity.isTerminating(): Boolean {
    return isFinishing || isDestroyed
}

/**
 * Extension method to provide show keyboard for [Activity].
 */
@RequiresApi(Build.VERSION_CODES.CUPCAKE)
fun Activity.showSoftKeyboard(
    view: View
) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
            as? InputMethodManager
        ?: return
    view.requestFocus()
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

/**
 * Extension method to provide hide keyboard for [Activity].
 */
fun Activity.hideSoftKeyboard() {
    val focus = currentFocus ?: return
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
            as? InputMethodManager
        ?: return
    inputMethodManager.hideSoftInputFromWindow(focus.windowToken, 0)
}

fun AppCompatActivity.hasFragment(
    fragmentClass: Class<*>
): Boolean {
    return supportFragmentManager.fragments.find { it.javaClass == fragmentClass } != null
}

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    @IdRes containerId: Int,
    shouldAddToBackStack: Boolean = false
) {
    supportFragmentManager.transact {
        replace(containerId, fragment)
        if (shouldAddToBackStack) addToBackStack(fragment::class.java.simpleName)
    }
}

/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragment(
    fragment: Fragment,
    @IdRes containerId: Int? = null,
    tag: String? = null
) {
    supportFragmentManager.transact {
        if (containerId == null) {
            add(fragment, tag)
            return
        }
        add(containerId, fragment, tag)
    }
}

/**
 * Setup actionbar
 */
fun AppCompatActivity.setupActionBar(
    @IdRes toolbarId: Int,
    @DrawableRes backIcon: Int? = null,
    action: ActionBar.() -> Unit = {}
): Toolbar {
    val toolbar = findViewById<Toolbar>(toolbarId)
    backIcon?.let { toolbar.setNavigationIcon(it) }
    setSupportActionBar(toolbar)
    supportActionBar?.run {
        action()
    }
    toolbar.setNavigationOnClickListener { onBackPressed() }

    return toolbar
}

fun FragmentActivity.setupActionBar(
    @IdRes toolbarId: Int,
    @DrawableRes backIcon: Int? = null,
    action: ActionBar.() -> Unit = {}
): Toolbar = (this as AppCompatActivity).setupActionBar(
    toolbarId, backIcon, action
)

/**
 * Runs a FragmentTransaction, then calls commit().
 */
internal inline fun FragmentManager.transact(
    action: FragmentTransaction.() -> Unit
) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun AppCompatActivity.setStatusBarColor(@ColorRes color: Int) {
    val window: Window = window
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, color)
}

fun AppCompatActivity.setLightStatusBarIcons() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decor: View = window.decorView
        var flags = decor.systemUiVisibility
        flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        decor.systemUiVisibility = flags
    }
}

fun AppCompatActivity.getFragmentOrNull(): Fragment? {
    val navFragment = supportFragmentManager
        .primaryNavigationFragment
        ?.childFragmentManager
        ?.fragments
        ?.lastOrNull()

    if (navFragment != null) return navFragment

    return supportFragmentManager.fragments.lastOrNull()
}