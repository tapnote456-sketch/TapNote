package uk.ac.tees.mad.tapnote.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.PendingIntentCompat
import uk.ac.tees.mad.tapnote.MainActivity
import uk.ac.tees.mad.tapnote.R

class ShakeNotificationManager(private val context: Context) {

    private val channelId = "shake_service"

    fun createNotification(): Notification {
        createChannel()

        val openAppIntent = Intent(context, MainActivity::class.java)

        val pendingIntent = PendingIntentCompat.getActivity(
            context,
            0,
            openAppIntent,
            android.app.PendingIntent.FLAG_UPDATE_CURRENT,
            false
        )

        return NotificationCompat.Builder(context, channelId)
            .setContentTitle("TapNote is running")
            .setContentText("Shake your phone to add a quick note")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setAutoCancel(false)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .build()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            channelId,
            "Shake Detection",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Keeps TapNote shake detection active"
            setShowBadge(false)
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        }

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}
