package com.example.push_messaging.data.messaging

import android.util.Log
import com.example.push_messaging.domain.PushMessagingRepository
import com.example.push_messaging.domain.entity.CardPushMessageEntity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class PushMessagingService(
    private val repository: PushMessagingRepository
) : FirebaseMessagingService() {

    private val messagingServiceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onNewToken(token: String) {
        Log.d("myLog", "newToken = $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val jsonString = message.data["card"] ?: return

        try {
            val cardPush = Json.decodeFromString<CardPushMessageEntity>(jsonString)

            messagingServiceScope.launch {
                repository.insertCard(cardPush)
            }
        } catch (e: Exception) {
            Log.d("myLog", "exception is $e")
        }

    }

    override fun onDestroy() {
        messagingServiceScope.cancel()
        super.onDestroy()
    }
}