package com.antmar.kardz.messaging

import android.content.Intent
import android.util.Log
import androidx.core.net.toUri
import com.antmar.kardz.presentation.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.net.URLEncoder

class PushMessagingService() : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("myLog", "service newToken = $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {

        val data = message.data

        Log.d("ReceiverLog", "$data")

        if (data["type"] == "new_card") {

//            val name = URLEncoder.encode(data["name"], "UTF-8")
//            val code = URLEncoder.encode(data["code"], "UTF-8")
//            val isBarcode = data["isBarcode"]

            val name = data["name"]
            val code = data["code"]
            val isBarcode = data["isBarcode"]



            val uri = "myapp://add_card/$name/$code/$isBarcode".toUri()

            Log.d("ReceiverLog", "$uri")

            val intent = Intent(Intent.ACTION_VIEW, uri, this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(intent)
        }
    }
}