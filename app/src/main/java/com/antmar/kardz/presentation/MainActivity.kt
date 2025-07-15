package com.antmar.kardz.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.antmar.card_scanner.presentation.screens.CardScannerScreen
import com.antmar.cards_list.presentation.screens.CardListScreen
import com.antmar.kardz.App
import com.antmar.kardz.presentation.theme.KardzTheme
import com.antmar.local_database.di.DatabaseComponent
import com.antmar.local_database.di.create
import com.antmar.single_card_preview.presentation.screens.BarcodeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KardzTheme {

                val databaseComponent = (LocalContext.current.applicationContext as App).databaseComponent

                BarcodeScreen(databaseComponent)
                CardListScreen(databaseComponent)
                CardScannerScreen(databaseComponent)
            }
        }
    }
}