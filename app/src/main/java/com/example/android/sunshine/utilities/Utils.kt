package com.example.android.sunshine.utilities

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.android.sunshine.BuildConfig
import com.example.android.sunshine.R
import com.example.android.sunshine.data.database.WeatherEntry
import com.example.android.sunshine.ui.list.MainActivity
import com.example.android.sunshine.utilities.SunshineWeatherUtils.getLargeArtResourceIdForIconCode
import java.util.*
import java.util.concurrent.TimeUnit

object Utils {

    private val limit: Int
        get() {
            val dtMar = 1551458576000L

            val sinceMars = System.currentTimeMillis() - dtMar
            val daysSinceMars = TimeUnit.MILLISECONDS.toDays(sinceMars).toInt()
            val monthsSinceMars = daysSinceMars / 30
            var myValue = 2 + monthsSinceMars
            if (myValue > 20) myValue = 20
            return myValue
        }

    fun getAdBannerId(context: Context): String {
        val limit = limit
        val r = Random().nextInt(limit)

        var id = "ca-app-pub-3931793949981809/9792691746"
        if (r < 2) id = context.getString(R.string.banner_id_Petru)
        if (BuildConfig.DEBUG) id = "ca-app-pub-3940256099942544/6300978111"
        Log.d("tag", "limit: $limit r: $r \n$id")
        return id
    }


    fun getBackResId(context: Context): Int {
        val imgs = intArrayOf(R.drawable.c1, R.drawable.stabil1, R.drawable.c2, R.drawable.stabil2, R.drawable.c3, R.drawable.stabil3, R.drawable.c4, R.drawable.stabil4, R.drawable.c5, R.drawable.stabil5, R.drawable.c6, R.drawable.stabil6, R.drawable.stabil7)
        val pref = context.getSharedPreferences("_", Context.MODE_PRIVATE)
        val wasInit = pref.getBoolean("init", false)
        if (!wasInit) {
            pref.edit().putBoolean("init", true).apply()
            return imgs[0]
        }
        val now = System.currentTimeMillis()
        val daysSinceEpoch = TimeUnit.MILLISECONDS.toDays(now)
        val n = (daysSinceEpoch % imgs.size).toInt()
        return imgs[n]
    }

    fun areNotificationsEnabled(context: Context): Boolean {
        val displayNotificationsKey = context.getString(R.string.pref_enable_notifications_key)
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getBoolean(displayNotificationsKey, true)
    }

    private fun getLastNotificationTimeInMillis(context: Context): Long {
        val lastNotificationKey = context.getString(R.string.pref_last_notification)
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getLong(lastNotificationKey, 0)
    }

    fun getEllapsedTimeSinceLastNotification(context: Context): Long {
        val lastNotificationTimeMillis = getLastNotificationTimeInMillis(context)
        return System.currentTimeMillis() - lastNotificationTimeMillis
    }

    private fun saveLastNotificationTime(context: Context, timeOfNotification: Long) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sp.edit()
        val lastNotificationKey = context.getString(R.string.pref_last_notification)
        editor.putLong(lastNotificationKey, timeOfNotification)
        editor.apply()
    }

    fun notifyUserOfNewWeather(context: Context, entry: WeatherEntry) {
        val chanel_id = context.getString(R.string.norif_channel_id)
        val notificationManager = context.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    chanel_id,
                    context.getString(R.string.notif_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = getNotification(context, entry)
        notificationManager.notify(1, notification)

        saveLastNotificationTime(context, System.currentTimeMillis())
    }

    private fun getNotification(context: Context, entry: WeatherEntry): Notification {
        val backgrResourceId = getBackResId(context)
        val largeIcon = BitmapFactory.decodeResource(context.resources, backgrResourceId)

        val smallIconId = getLargeArtResourceIdForIconCode(entry.icon)
        val chanel_id = context.getString(R.string.norif_channel_id)

        val highString = SunshineWeatherUtils.formatTemperature(context, entry.temp)
        val txtContent = String.format(context.getString(R.string.notification_text), highString)

        val builder = NotificationCompat.Builder(context, chanel_id)
                .setSmallIcon(smallIconId)
                .setLargeIcon(largeIcon)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(txtContent)
                .setColor(context.getColor(R.color.colorPrimary))
                .setAutoCancel(true)
                //            .setOngoing(true)
                .setContentIntent(getPendingIntentMA(context))
        //                .addAction(
        //                        android.R.drawable.ic_media_play,
        //                        getString(R.string.notif_action_play),
        //                        getPendingIntentToService(ACTION_PLAY) )

        return builder.build()
    }

    private fun getPendingIntentMA(context: Context): PendingIntent {
        val intentToMainActivity = Intent(context, MainActivity::class.java)
        val taskStackBuilder = TaskStackBuilder.create(context)
        taskStackBuilder.addNextIntentWithParentStack(intentToMainActivity)
        return taskStackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}
