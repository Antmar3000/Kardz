package com.antmar.single_card_preview.domain.generate_barcode

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.google.zxing.common.BitMatrix


fun generateBarcodeBitmap(
    info : BarcodeInfo,
    width : Int = 700,
    height : Int = 350
) : ImageBitmap {

    val bitMatrix = MultiFormatWriter().encode(info.code, info.format, width, height)
    val bitmap = createBitmap(width, height, Bitmap.Config.RGB_565)

    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap[x, y] = if (bitMatrix.get(x, y)) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
        }
    }

    return bitmap.asImageBitmap()
}

data class BarcodeInfo (
    val code : String,
    val format : BarcodeFormat
)

fun generateQrCodeBitmap(
    info: BarcodeInfo,
    size: Int = 450,
    margin: Int = 1
): ImageBitmap {

    val bitMatrix: BitMatrix = MultiFormatWriter().encode(
        info.code,
        BarcodeFormat.QR_CODE,
        size,
        size,
        mapOf(
            com.google.zxing.EncodeHintType.MARGIN to margin,
            com.google.zxing.EncodeHintType.CHARACTER_SET to "UTF-8"
        )
    )


    val bitmap = createBitmap(size, size)
    for (x in 0 until size) {
        for (y in 0 until size) {
            bitmap[x, y] = if (bitMatrix.get(x, y)) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
        }
    }

    return bitmap.asImageBitmap()
}