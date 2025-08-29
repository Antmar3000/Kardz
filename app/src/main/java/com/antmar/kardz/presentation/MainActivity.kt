package com.antmar.kardz.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import com.antmar.kardz.App
import com.antmar.kardz.presentation.screens.MainScreen
import com.antmar.kardz.presentation.theme.KardzTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KardzTheme {

                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.dark(
                        MaterialTheme.colorScheme.background.toArgb(),
                    ),
                    navigationBarStyle = SystemBarStyle.dark(
                        MaterialTheme.colorScheme.background.toArgb()
                    )
                )

                val databaseComponent =
                    (LocalContext.current.applicationContext as App).databaseComponent

                MainScreen(databaseComponent)

            }
        }
    }
}

