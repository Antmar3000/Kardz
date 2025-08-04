package com.antmar.kardz.presentation.screens

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.antmar.card_scanner.presentation.screens.CardAdditionScreen
import com.antmar.cards_list.presentation.screens.CardListScreen
import com.antmar.core.navigation.NavRoutes
import com.antmar.core.navigation.Navigator
import com.antmar.local_database.di.DatabaseComponent
import com.antmar.single_card_preview.presentation.screens.BarcodeScreen

@Composable
fun MainScreen(databaseComponent: DatabaseComponent) {

    val navController = rememberNavController()
    val activity = LocalActivity.current

    val navigator = remember(navController) {
        object : Navigator {
            override fun navigate(route: NavRoutes) = navController.navigate(route.route)

            override fun popBackStack() {
                if (!navController.popBackStack()) {
                    activity?.finish()
                }
            }
        }
    }
    Scaffold { padding ->
        NavHost(
            navController,
            startDestination = NavRoutes.LIST.route,
            modifier = Modifier.padding(padding)
        )
        {
            composable(route = NavRoutes.LIST.route) {
                CardListScreen(databaseComponent, navigator)
            }

            composable(route = NavRoutes.BARCODE.route) {
                BarcodeScreen(databaseComponent, navigator)
            }

            composable(route = NavRoutes.SCANNER.route) {
                CardAdditionScreen(databaseComponent, navigator)
            }
        }
    }

}