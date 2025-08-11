package com.antmar.card_scanner.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoundedTextField(text: String, input: MutableState<String>, isBarcode : Boolean) {

    BasicTextField(
        value = input.value,
        onValueChange = { input.value = it },
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .border(
                2.dp,
                MaterialTheme.colorScheme.onBackground,
                shape = CircleShape
            )
            .background(
                color = MaterialTheme.colorScheme.onBackground,
                shape = CircleShape
            ),
        textStyle = LocalTextStyle.current.copy(
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType =
                if (isBarcode) KeyboardType.Number else KeyboardType.Text
        ),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (input.value.isEmpty()) {
                    Text(
                        text,
                        color = Color.Black.copy(alpha = 0.7f),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    )
                }
                innerTextField()
            }
        })
}