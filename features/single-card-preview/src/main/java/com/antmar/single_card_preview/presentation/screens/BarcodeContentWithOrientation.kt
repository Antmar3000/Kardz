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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.core.ui.getColorBasedOnBackground
import com.antmar.single_card_preview.domain.generate_barcode.BarcodeInfo
import com.antmar.single_card_preview.domain.generate_barcode.generateBarcodeBitmap
import com.antmar.single_card_preview.domain.generate_barcode.generateQrCodeBitmap

@Composable
fun BarcodeContentWithOrientation(
    card: CardUIEntity,
    onCloseClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    codeInfo: BarcodeInfo,
    isPortrait: Boolean
) {

    if (isPortrait) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete_icon",
                    modifier = Modifier
                        .clickable(
                            onClick = onDeleteClick
                        )
                        .size(40.dp)
                        .padding(start = 12.dp),
                    tint = getColorBasedOnBackground(card.color)

                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "edit_icon",
                    modifier = Modifier
                        .clickable(
                            onClick = onEditClick
                        )
                        .size(30.dp),
                    tint = getColorBasedOnBackground(card.color)
                )

                Spacer(modifier = Modifier.weight(1f))

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
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(3f),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(modifier = Modifier.weight(1f).scale(1.3f),
                            bitmap = if (card.isBarcode) generateBarcodeBitmap(codeInfo)
                            else generateQrCodeBitmap(codeInfo),
                            contentDescription = "generateBitmap",

                        )

                        Text(
                            text = card.code.formatGrouped(),
                            fontSize = 32.sp,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 24.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.End
            ) {

                Icon(
                    imageVector = Icons.Default.Close,
                    "close_icon",
                    modifier = Modifier
                        .clickable(
                            onClick = onCloseClick
                        )
                        .size(50.dp),
                    tint = getColorBasedOnBackground(card.color)
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "edit_icon",
                    modifier = Modifier
                        .clickable(
                            onClick = onEditClick
                        )
                        .size(40.dp),
                    tint = getColorBasedOnBackground(card.color)
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete_icon",
                    modifier = Modifier
                        .clickable(
                            onClick = onDeleteClick
                        )
                        .size(50.dp),
                    tint = getColorBasedOnBackground(card.color)

                )
            }

        }
    }
}