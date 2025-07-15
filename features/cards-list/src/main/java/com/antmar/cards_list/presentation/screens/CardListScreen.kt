package com.antmar.cards_list.presentation.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.antmar.cards_list.di.CardsListComponent
import com.antmar.cards_list.di.create
import com.antmar.core.di.injectViewModel
import com.antmar.local_database.di.DatabaseComponent
import com.antmar.local_database.di.create

@Composable
fun CardListScreen(databaseComponent: DatabaseComponent) {

    Log.d("myLog", databaseComponent.provideDatabase().toString())

    val vm = CardsListComponent::class.create(databaseComponent).cardsListViewModelFactory().injectViewModel()

}