package com.antmar.kardz.messaging

import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

//class KardzFirebaseMessagingService : FirebaseMessagingService() {
//
//    override fun onNewToken(token: String) {
//
//        Log.d("myLog", "newToken = $token")
//
//    }
//
//
//    override fun onMessageReceived(message: RemoteMessage) {
//
//        message.data.let { data ->
//            Log.d("myLog", "message data = $data")
//        }
//        message.notification?.let { notification ->
//            Log.d("myLog", "notification = ${notification.body}")
//        }
//
//    }
//}