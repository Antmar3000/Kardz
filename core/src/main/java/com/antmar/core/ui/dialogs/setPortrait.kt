package com.antmar.core.ui.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo

@SuppressLint("SourceLockedOrientationActivity")
fun setPortrait (activity: Activity?) {
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

fun setUnspecified (activity: Activity?) {
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}