package com.antmar.single_card_preview.presentation.screens

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antmar.core.di.KIViewModel
import com.antmar.core.domain.entity.CardId
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.core.navigation.NavRoutes
import com.antmar.core.navigation.Navigator
import com.antmar.core.ui.dialogs.DeleteCardDialogContent
import com.antmar.core.ui.dialogs.HorizontalExpandDialog
import com.antmar.core.ui.dialogs.setPortrait
import com.antmar.core.ui.dialogs.setUnspecified
import com.antmar.local_database.di.DatabaseComponent
import com.antmar.single_card_preview.di.SingleCardComponent
import com.antmar.single_card_preview.di.create
import com.antmar.single_card_preview.domain.generate_barcode.BarcodeInfo
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

    val activity = LocalActivity.current
    val orientation = LocalConfiguration.current.orientation

    fun onCloseClick() {
        setPortrait(activity)
        navigator.popBackStack()
    }

    fun onEditClick() {
        setPortrait(activity)
        if (currentCard != null) {
            viewModel.sendCardId(CardId(currentCard.id))
            navigator.navigate(NavRoutes.SCANNER)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.collectId()
    }

    DisposableEffect(Unit) {
        setUnspecified(activity)
        onDispose {
            setPortrait(activity)
        }
    }

    BackHandler(enabled = true) {
        setPortrait(activity)
        navigator.popBackStack()
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
        { onCloseClick() },
        viewModel::toggleDeleteDialog,
        { onEditClick() },
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(180.dp))
            }

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