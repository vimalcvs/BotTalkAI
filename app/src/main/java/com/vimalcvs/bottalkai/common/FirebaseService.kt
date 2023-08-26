package com.vimalcvs.bottalkai.common

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.vimalcvs.bottalkai.R


class FirebaseService : FirebaseMessagingService() {

    val CHANNEL_ID = "BotTalkAI"
    val CHANNEL_NAME = "BotTalkAI"
    val NOTIF_ID = 0

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("SellerFirebaseService ", "Refreshed token :: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.i("SellerFirebaseService ", "Message :: $message")


        val notif = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.app_icon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()


        val notifManger = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notifManger.notify(NOTIF_ID, notif)


    }


}
