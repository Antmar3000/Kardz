package com.antmar.single_card_preview.domain.generate_barcode

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set


fun generateBarcodeBitmap(
    text : String,
    format: BarcodeFormat = BarcodeFormat.EAN_13,
    width : Int = 600,
    height : Int = 300
) : ImageBitmap {

    val bitMatrix = MultiFormatWriter().encode(text, format, width, height)
    val bitmap = createBitmap(width, height, Bitmap.Config.RGB_565)

    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap[x, y] = if (bitMatrix.get(x, y)) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
        }
    }

    return bitmap.asImageBitmap()
}