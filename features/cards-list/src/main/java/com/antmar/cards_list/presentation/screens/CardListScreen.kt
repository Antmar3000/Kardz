package com.antmar.cards_list.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antmar.cards_list.di.CardsListComponent
import com.antmar.cards_list.di.create
import com.antmar.core.di.KIViewModel
import com.antmar.core.di.injectViewModel
import com.antmar.core.navigation.NavRoutes
import com.antmar.core.navigation.Navigator
import com.antmar.local_database.di.DatabaseComponent
import com.antmar.local_database.di.create

@Composable
fun CardListScreen(databaseComponent: DatabaseComponent, navigator: Navigator) {

    Log.d("myLog", databaseComponent.provideDatabase().toString())

//    val vm = CardsListComponent::class.create(databaseComponent).cardsListViewModelFactory().injectViewModel()

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

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(
                items = list,
                key = { it.id }
            ) { card ->
                CardListItem(card, { navigateToCardPreview(card.id) })
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