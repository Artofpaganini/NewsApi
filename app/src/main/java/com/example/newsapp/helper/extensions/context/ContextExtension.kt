package com.example.newsapp.helper.extensions.context

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.makeToast(
    @StringRes messageRes: Int,
    duration: Int = Toast.LENGTH_LONG
) {
    makeToast(getString(messageRes), duration)
}

fun Context.makeToast(
    message: String,
    duration: Int = Toast.LENGTH_LONG
) {
    Toast.makeText(this, message, duration).show()
}

fun Context.getPixelByDensity(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun Context.getPixelByDensity(dp: Float): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun Context.getScreenWidthOrNull(): Int? {
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        ?: return null
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay?.getMetrics(displayMetrics)
        ?: return null

    return displayMetrics.widthPixels
}
