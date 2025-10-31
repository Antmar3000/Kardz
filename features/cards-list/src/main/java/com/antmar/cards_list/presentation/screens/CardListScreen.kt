package com.antmar.cards_list.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antmar.cards_list.di.CardsListComponent
import com.antmar.cards_list.di.create
import com.antmar.core.di.KIViewModel
import com.antmar.core.domain.entity.CardId
import com.antmar.core.navigation.NavRoutes
import com.antmar.core.navigation.Navigator
import com.antmar.core.ui.dialogs.DeleteCardDialogContent
import com.antmar.core.ui.dialogs.HorizontalExpandDialog
import com.antmar.local_database.di.DatabaseComponent

@Composable
fun CardListScreen(databaseComponent: DatabaseComponent, navigator: Navigator) {

    val component = CardsListComponent::class.create(databaseComponent)
    val viewModel = KIViewModel(component.cardsListViewModelFactory())

    val list = viewModel.allCardsListState.collectAsStateWithLifecycle().value

    val dialogState = viewModel.dialogState.collectAsStateWithLifecycle().value

    val activity = LocalActivity.current
    BackHandler (enabled = true) {
        activity?.finish()
    }

    LaunchedEffect(Unit) {
        viewModel.collectCards()
        viewModel.sendCardId(CardId(-1))
    }

    fun deleteCard() {
        viewModel.deleteCard(dialogState)
        viewModel.toggleDeleteDialog(-1)
    }

    fun navigateToAddCardScreen() {
        navigator.navigate(NavRoutes.SCANNER)
    }

    if (dialogState != -1) {

        HorizontalExpandDialog(onDismissRequest = { viewModel.toggleDeleteDialog(-1) }) {
            DeleteCardDialogContent(
                onConfirm = { deleteCard() },
                onCancel = { viewModel.toggleDeleteDialog(-1) }
            )
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        LazyColumn {
            items(
                items = list,
                key = { it.id }
            ) { card ->
                SwipeToDeleteCardListItem(
                    card = card,
                    navigate = {
                        viewModel.sendCardId(CardId(card.id))
                        navigator.navigate(NavRoutes.BARCODE)
                    },
                    onSwipe = { viewModel.toggleDeleteDialog(card.id) })
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp),
            onClick = { navigateToAddCardScreen() },
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = Color.White
        ) {
            Icon(Icons.Filled.Add, "FAB_add_card")
        }
    }
}