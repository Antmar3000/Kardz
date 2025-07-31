package com.antmar.single_card_preview.presentation.screens

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
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

    val component = SingleCardComponent::class.create(databaseComponent)

    val viewModel = KIViewModel(component.singleCardViewModelFactory())

    var inputState by remember { mutableStateOf("") }

    val currentCard = viewModel.currentCardState.collectAsStateWithLifecycle().value

    fun navigateBack () = navigator.popBackStack()

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

    CurrentCardUI(currentCard, { navigateBack() })

}

@Composable
fun CurrentCardUI(
    card: CardUIEntity?,
    onClick : () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(card?.color ?: 0xFF87CEEB)
        )
    ) {
        if (card != null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row (
                    horizontalArrangement = Arrangement.End
                ) {
                    Spacer(modifier = Modifier.weight(8f))

                    Icon(imageVector = Icons.Default.Close,
                        "close_icon",
                        modifier = Modifier
                            .clickable(
                            onClick = onClick
                        )
                            .size(60.dp)
                            .padding(end = 12.dp),
                        tint = Color.Red)
                }

                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = card.name,
                    fontSize = 40.sp,
                    style = TextStyle(
                        color = Color.White
                    )
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Image(
                            bitmap = generateBarcodeBitmap(card.code.toString()),
                            contentDescription = "generateBitmap",
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(3f)
                        )

                        Text(
                            modifier = Modifier.weight(1f),
                            text = card.code.formatGrouped(),
                            fontSize = 32.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(120.dp))
            }
        } else {
            CircularProgressIndicator()
        }
    }
}

fun Long.formatGrouped(): String {
    val s = this.toString()
    return when (s.length) {
        12 -> {s.chunked(4).joinToString ( " " )}
        13 -> {
            val tail = s.drop(1)
            listOf(s.first().toString()) + tail.chunked(3).joinToString ( " ")
            s
        }
        else -> s
    }
}