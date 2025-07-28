package com.antmar.single_card_preview.presentation.screens

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antmar.core.di.KIViewModel
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.core.navigation.Navigator
import com.antmar.local_database.di.DatabaseComponent
import com.antmar.single_card_preview.di.SingleCardComponent
import com.antmar.single_card_preview.di.create
import com.antmar.single_card_preview.domain.generate_barcode.generateBarcodeBitmap

@Composable
fun BarcodeScreen(databaseComponent: DatabaseComponent, navigator: Navigator) {

    Log.d("myLog", databaseComponent.provideDatabase().toString())

    val component = SingleCardComponent::class.create(databaseComponent)

//    val vm = component.singleCardViewModelFactory().injectViewModel()

    val viewModel = KIViewModel(component.singleCardViewModelFactory())
    Log.d("myLog", viewModel.toString())

    var inputState by remember { mutableStateOf("") }

    val currentCard = viewModel.currentCardState.collectAsStateWithLifecycle().value

    val barcodeBitmap = remember { mutableStateOf(generateBarcodeBitmap("111111111111")) }

//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.SpaceAround,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        TextField(
//            value = inputState,
//            onValueChange = { inputState = it }
//        )
//
//        Button(onClick = { barcodeBitmap.value = generateBarcodeBitmap(inputState) }) {
//            Text("Generate")
//        }
//
//        Image(
//            bitmap = barcodeBitmap.value,
//            contentDescription = "bitmap"
//        )
//    }

    CurrentCardUI(currentCard)

}

@Composable
fun CurrentCardUI (card : CardUIEntity?) {
    Card(
        modifier = Modifier.fillMaxSize()
            .padding(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(card?.color ?: 0xFF87CEEB)
        )
    ) {
        if (card != null) {
            Column (modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround) {

                Text(text = card.name,
                    fontSize = 24.sp,
                    style = TextStyle(
                        color = Color.DarkGray
                    ))

                Image(bitmap = generateBarcodeBitmap(card.code.toString()),
                    contentDescription = "generateBitmap",
                    modifier = Modifier.fillMaxWidth().padding(24.dp))

                Text(text = card.code.toString(),
                    fontSize = 16.sp)
            }
        }
        else {
            CircularProgressIndicator()
        }
    }
}