package com.antmar.kardz.presentation.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.antmar.card_scanner.presentation.screens.CardAdditionScreen
import com.antmar.cards_list.presentation.screens.CardListScreen
import com.antmar.core.navigation.NavRoutes
import com.antmar.core.navigation.Navigator
import com.antmar.kardz.App
import com.antmar.local_database.di.DatabaseComponent
import com.antmar.single_card_preview.presentation.screens.BarcodeScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.delay
import java.net.URLDecoder

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val activity = LocalActivity.current
    val context = LocalContext.current

    val databaseComponent = (context.applicationContext as App).databaseComponent

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

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) { padding ->
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

            composable(route =
                "${NavRoutes.SCANNER.route}?" +
            "name={name}&code={code}&isBarcode={isBarcode}",
                arguments = listOf(
                    navArgument("name") {defaultValue = ""},
                    navArgument("code") { defaultValue = "" },
                    navArgument("isBarcode") { defaultValue = "true" }
                ),
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern = "myapp://add_card?" +
                                "title={name}&barcode={code}&isBarcode={isBarcode}"
                    }
                )
            ) { backStackEntry ->

                val name = URLDecoder.decode(backStackEntry.arguments?.getString("name") ?: "", "UTF-8")
                val code = URLDecoder.decode(backStackEntry.arguments?.getString("code") ?: "", "UTF-8")
                val isBarcode = backStackEntry.arguments?.getString("isBarcode")?.toBoolean()

                CardAdditionScreen(
                    databaseComponent,
                    navigator,
                    prefillName = name,
                    prefillCode = code,
                    prefillIsBarcode = isBarcode)
            }
        }
    }

    var hasNotificationPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasNotificationPermission = isGranted
    }

    LaunchedEffect(hasNotificationPermission) {
        if (hasNotificationPermission) {

            val firebaseApps = FirebaseApp.getApps(context)

            if (firebaseApps.isNotEmpty()) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val token = task.result
                        Log.d("myLog", "main screen token = $token")
                    }
                }
            } else {
                Log.d("myLog", "firebase not initialized")
            }


        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

}