package com.antmar.single_card_preview.presentation.screens

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.createBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.antmar.core.di.injectViewModel
import com.antmar.local_database.di.DatabaseComponent
import com.antmar.single_card_preview.di.SingleCardComponent
import com.antmar.single_card_preview.di.create
import com.antmar.single_card_preview.domain.generate_barcode.generateBarcodeBitmap

@Composable
fun BarcodeScreen(databaseComponent: DatabaseComponent) {

    Log.d("myLog", databaseComponent.provideDatabase().toString())

    val vm = SingleCardComponent::class.create(databaseComponent).singleCardViewModelFactory().injectViewModel()

    var inputState by remember { mutableStateOf("") }

    val barcodeBitmap = remember { mutableStateOf(generateBarcodeBitmap("111111111111")) }

    Scaffold {

        Column (modifier = Modifier.padding(it)) {

            TextField(
                value = inputState,
                onValueChange = { inputState = it}
            )

            Button( onClick = { barcodeBitmap.value = generateBarcodeBitmap(inputState)}) {
                Text("Generate")
            }

            Image(
                bitmap = barcodeBitmap.value ?: createBitmap(600, 300, Bitmap.Config.RGB_565).asImageBitmap(),
                contentDescription = "bitmap"
            )
        }
    }
}