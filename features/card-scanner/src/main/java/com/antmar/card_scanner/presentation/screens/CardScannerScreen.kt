package com.antmar.card_scanner.presentation.screens

import android.util.Log
import androidx.compose.runtime.Composable
import com.antmar.card_scanner.di.CardScannerComponent
import com.antmar.card_scanner.di.create
import com.antmar.core.di.injectViewModel
import com.antmar.local_database.di.DatabaseComponent

@Composable
fun CardScannerScreen (databaseComponent: DatabaseComponent) {

    Log.d("myLog", databaseComponent.provideDatabase().toString())

    val vm = CardScannerComponent::class.create(databaseComponent).cardScannerViewModelFactory().injectViewModel()

}