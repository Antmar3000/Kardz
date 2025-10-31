package com.antmar.kardz.presentation

import android.annotation.SuppressLint
import android.app.ComponentCaller
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import com.antmar.core.ui.dialogs.setPortrait
import com.antmar.core.ui.views.DotWaveLoader
import com.antmar.kardz.App
import com.antmar.kardz.messaging.DeepLinkManager
import com.antmar.kardz.presentation.screens.MainScreen
import com.antmar.kardz.presentation.theme.KardzTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setPortrait(this)

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
                MainScreen()
            }
        }
    }

    override fun onNewIntent(intent: Intent, caller: ComponentCaller) {
        super.onNewIntent(intent, caller)

        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        Log.d("ReceiverLog", "intent = ${intent?.data}")

        intent?.data?.let { uri ->
            if (uri.scheme == "myapp" && uri.host == "add_card") {
                Log.d("ReceiverLog", "scheme = $uri")

                DeepLinkManager.setPendingUri(uri)
            }
        }
    }
}

