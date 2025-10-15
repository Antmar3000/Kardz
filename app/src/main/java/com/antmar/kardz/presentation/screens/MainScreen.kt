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

            composable(route = NavRoutes.SCANNER.route) {
                CardAdditionScreen(databaseComponent, navigator)
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
        if (isGranted) {

        } else {
            Toast.makeText(context, "Notifications disabled", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(hasNotificationPermission) {
        delay(1000)
        if (hasNotificationPermission) {

            val firebaseApps = FirebaseApp.getApps(context)

            if (firebaseApps.isNotEmpty()) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val token = task.result
                        Log.d("myLog", "token = $token")
                    }
                }
            } else {
                Log.d("myLog", "firebase not initialized")
            }


        } else {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

}