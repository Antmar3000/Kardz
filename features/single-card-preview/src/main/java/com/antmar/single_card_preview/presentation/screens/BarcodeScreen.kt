package com.antmar.single_card_preview.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antmar.core.di.KIViewModel
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.core.navigation.Navigator
import com.antmar.core.ui.dialogs.HorizontalExpandDialog
import com.antmar.core.ui.getColorBasedOnBackground
import com.antmar.local_database.di.DatabaseComponent
import com.antmar.single_card_preview.di.SingleCardComponent
import com.antmar.single_card_preview.di.create
import com.antmar.single_card_preview.domain.generate_barcode.BarcodeInfo
import com.antmar.single_card_preview.domain.generate_barcode.generateBarcodeBitmap
import com.antmar.single_card_preview.domain.generate_barcode.generateQrCodeBitmap
import com.google.zxing.BarcodeFormat

@Composable
fun BarcodeScreen(databaseComponent: DatabaseComponent, navigator: Navigator) {

    val component = SingleCardComponent::class.create(databaseComponent)

    val viewModel = KIViewModel(component.singleCardViewModelFactory())

    val currentCard = viewModel.currentCardState.collectAsStateWithLifecycle().value
    val dialogState = viewModel.dialogState.collectAsStateWithLifecycle().value

    fun deleteCard() {
        viewModel.toggleDeleteDialog()
        viewModel.deleteCard(currentCard?.id ?: 0)
        navigator.popBackStack()
    }

    if (dialogState) {
        HorizontalExpandDialog(onDismissRequest = { viewModel.toggleDeleteDialog() }) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "Delete card?",
                    color = Color.DarkGray,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "do_delete",
                        modifier = Modifier
                            .clickable(
                                onClick = { deleteCard() }
                            )
                            .size(40.dp),
                        tint = Color.DarkGray)

                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "do_not_delete",
                        modifier = Modifier
                            .clickable(
                                onClick = { viewModel.toggleDeleteDialog() }
                            )
                            .size(40.dp),
                        tint = Color.DarkGray)
                }
            }
        }
    }

    CurrentCardUI(currentCard, { navigator.popBackStack() }, viewModel::toggleDeleteDialog )

}

@Composable
fun CurrentCardUI(
    card: CardUIEntity?,
    onCloseClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(card?.color ?: 0xFF87CEEB)
        )
    ) {
        if (card != null) {

            val codeInfo = BarcodeInfo(
                code = card.code,
                format = if (card.code.length == 12) {
                    BarcodeFormat.CODE_128
                } else {
                    BarcodeFormat.EAN_13
                }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.padding(top = 8.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete_icon",
                        modifier = Modifier
                            .clickable(
                                onClick = onDeleteClick
                            )
                            .size(60.dp)
                            .padding(start = 12.dp),
                        tint = getColorBasedOnBackground(card.color)

                    )

                    Spacer(modifier = Modifier.weight(8f))

                    Icon(
                        imageVector = Icons.Default.Close,
                        "close_icon",
                        modifier = Modifier
                            .clickable(
                                onClick = onCloseClick
                            )
                            .size(60.dp)
                            .padding(end = 12.dp),
                        tint = getColorBasedOnBackground(card.color)
                    )
                }

                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = card.name,
                    fontSize = 40.sp,
                    style = TextStyle(
                        color = getColorBasedOnBackground(card.color)
                    )
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(
                            bitmap = if (card.isBarcode) generateBarcodeBitmap(codeInfo)
                            else generateQrCodeBitmap(codeInfo),
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

fun String.formatGrouped(): String {
    return when (this.length) {

        12 -> {
            this.chunked(4).joinToString(" ")
        }

        13 -> {
            val tail = this.drop(1)
            this.first().toString() + " " + tail.chunked(3).joinToString(" ")
        }

        else -> this
    }
}