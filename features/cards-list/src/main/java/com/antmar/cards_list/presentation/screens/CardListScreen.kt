package com.antmar.cards_list.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antmar.cards_list.di.CardsListComponent
import com.antmar.cards_list.di.create
import com.antmar.core.di.KIViewModel
import com.antmar.core.navigation.NavRoutes
import com.antmar.core.navigation.Navigator
import com.antmar.core.ui.dialogs.HorizontalExpandDialog
import com.antmar.local_database.di.DatabaseComponent

@Composable
fun CardListScreen(databaseComponent: DatabaseComponent, navigator: Navigator) {

    val component = CardsListComponent::class.create(databaseComponent)
    val viewModel = KIViewModel(component.cardsListViewModelFactory())

    fun navigateToCardPreview(id: Int) {
        viewModel.sendCardId(id)
        navigator.navigate(NavRoutes.BARCODE)
    }

    fun navigateToScannerScreen() {
        navigator.navigate(NavRoutes.SCANNER)
    }

    val list = viewModel.allCardsListState.collectAsStateWithLifecycle().value

    val dialogState = viewModel.dialogState.collectAsStateWithLifecycle().value

    if (dialogState != -1) {
        HorizontalExpandDialog(onDismissRequest = { viewModel.toggleDeleteDialog(-1) }) {
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
                    horizontalArrangement = Arrangement.spacedBy(100.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "do_delete",
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    viewModel.deleteCard(dialogState)
                                    viewModel.toggleDeleteDialog(-1) }
                            )
                            .size(40.dp))

                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "do_not_delete",
                        modifier = Modifier
                            .clickable(
                                onClick = { viewModel.toggleDeleteDialog(-1) }
                            )
                            .size(40.dp))
                }
            }
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
                    navigate = { navigateToCardPreview(card.id) },
                    onSwipe = { viewModel.toggleDeleteDialog(card.id) })
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp),
            onClick = { navigateToScannerScreen() },
            shape = CircleShape
        ) {
            Icon(Icons.Filled.Add, "FAB_add_card")
        }
    }
}