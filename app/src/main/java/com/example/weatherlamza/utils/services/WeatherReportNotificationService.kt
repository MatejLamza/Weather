package com.example.weatherlamza.utils.services

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.weatherlamza.MainActivity
import com.example.weatherlamza.R
import kotlin.random.Random

class WeatherReportNotificationService(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val notificationBuilder by lazy {
        NotificationCompat.Builder(context, MainActivity.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_storm)
    }

    fun showNotification(title: String, description: String) {
        val notification = notificationBuilder
            .setContentTitle(title)
            .setContentText(description)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }

}
