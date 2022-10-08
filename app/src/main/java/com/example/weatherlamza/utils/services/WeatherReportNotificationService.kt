package com.example.weatherlamza.utils.services

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.weatherlamza.MainActivity
import com.example.weatherlamza.R

class WeatherReportNotificationService(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification() {
        val notification = NotificationCompat.Builder(context, MainActivity.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText("Its gonna rain!!")
            .setContentTitle("Storm incoming")
            .build()

        notificationManager.notify(1, notification)
    }

}
