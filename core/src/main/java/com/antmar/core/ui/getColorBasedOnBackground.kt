package com.antmar.core.ui

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils

fun getColorBasedOnBackground (color : Long) : Color {
    val luminance = ColorUtils.calculateLuminance(color.toInt())
    return if (luminance < 0.5) Color.White else Color.Black
}