package com.antmar.single_card_preview.presentation.screens

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.activity.compose.LocalActivity
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antmar.core.di.KIViewModel
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.core.navigation.NavRoutes
import com.antmar.core.navigation.Navigator
import com.antmar.core.ui.dialogs.DeleteCardDialogContent
import com.antmar.core.ui.dialogs.HorizontalExpandDialog
import com.antmar.core.ui.getColorBasedOnBackground
import com.antmar.local_database.di.DatabaseComponent
import com.antmar.single_card_preview.di.SingleCardComponent
import com.antmar.single_card_preview.di.create
import com.antmar.single_card_preview.domain.generate_barcode.BarcodeInfo
import com.antmar.single_card_preview.domain.generate_barcode.generateBarcodeBitmap
import com.antmar.single_card_preview.domain.generate_barcode.generateQrCodeBitmap
import com.google.zxing.BarcodeFormat

@SuppressLint("SourceLockedOrientationActivity")
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

    val activity = LocalActivity.current
    val orientation = LocalConfiguration.current.orientation

    DisposableEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    if (dialogState) {
        HorizontalExpandDialog(onDismissRequest = { viewModel.toggleDeleteDialog() }) {
            DeleteCardDialogContent(
                onConfirm = { deleteCard() },
                onCancel = { viewModel.toggleDeleteDialog() }
            )
        }
    }

    CurrentCardUI(
        currentCard,
        { navigator.popBackStack() },
        viewModel::toggleDeleteDialog,
        {
            if (currentCard != null) {
                viewModel.sendCardId(currentCard.id)
                navigator.navigate(NavRoutes.SCANNER)
            }
        },
        orientation == Configuration.ORIENTATION_PORTRAIT
    )

}

@Composable
fun CurrentCardUI(
    card: CardUIEntity?,
    onCloseClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    isPortrait: Boolean
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

            BarcodeContentWithOrientation(
                card,
                onCloseClick,
                onDeleteClick,
                onEditClick,
                codeInfo,
                isPortrait
            )

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