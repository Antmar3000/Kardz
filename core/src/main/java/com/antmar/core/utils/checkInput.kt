package com.antmar.core.utils

fun checkInput(
    name: String,
    isBarcode: Boolean,
    length: Int
): InputCheckedValue {

    return if (name.isNotEmpty()) {
        if (isBarcode) {
            if (length == 12 || length == 13) {
                InputCheckedValue.VALID_CODE
            } else {
                InputCheckedValue.INVALID_CODE
            }
        } else {
            InputCheckedValue.VALID_CODE
        }
    } else {
        InputCheckedValue.EMPTY_CODE
    }

}

enum class InputCheckedValue {
    VALID_CODE,
    INVALID_CODE,
    EMPTY_CODE
}