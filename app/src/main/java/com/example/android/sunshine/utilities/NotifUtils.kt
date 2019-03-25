package com.example.android.sunshine.utilities

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.preference.PreferenceManager
import android.text.format.DateUtils
import android.text.format.DateUtils.HOUR_IN_MILLIS
import androidx.core.app.NotificationCompat
import com.example.android.sunshine.R
import com.example.android.sunshine.data.database.WeatherEntry
import com.example.android.sunshine.ui.main.MainActivity
import com.example.android.sunshine.utilities.Utils.getBackResId

object NotifUtils {

    private fun areNotificationsEnabled(context: Context): Boolean {
        val displayNotificationsKey = context.getString(R.string.pref_enable_notifications_key)
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getBoolean(displayNotificationsKey, true)
    }

    private fun getLastNotificationTimeInMillis(context: Context): Long {
        val lastNotificationKey = context.getString(R.string.pref_last_notification)
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getLong(lastNotificationKey, 0)
    }

    private fun getEllapsedTimeSinceLastNotification(context: Context): Long {
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

    private fun notifyUserOfNewWeather(context: Context, entry: WeatherEntry) {
        val chanelId = context.getString(R.string.norif_channel_id)
        val notificationManager = context.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    chanelId,
                    context.getString(R.string.notif_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = buildNotif(context, entry)
        notificationManager.notify(1, notification)

        saveLastNotificationTime(context, System.currentTimeMillis())
    }

    private fun buildNotif(context: Context, entry: WeatherEntry): Notification {
        val backgrResourceId = getBackResId()
        val largeIcon = BitmapFactory.decodeResource(context.resources, backgrResourceId)

        val smallIconId = SunshineWeatherUtils.getLargeArtResourceIdForIconCode(entry.iconCodeOWM)
        val chanelId = context.getString(R.string.norif_channel_id)

        val highString = SunshineWeatherUtils.formatTemperature(context, entry.temperature)

        val description = SunshineWeatherUtils.getStringForWeatherCondition(context, entry.weatherId)
//        val weatherTimeMills = SunshineDateUtils.getCityDate(context, entry.date.time)
//        val weatherDate = SimpleDateFormat("HH mm", Locale.getDefault()).format(weatherTimeMills)
//        val weatherDate = DateFormat.getTimeInstance(DateFormat.SHORT).format(weatherTimeMills)

        val cityName = context.getString(R.string.app_name)
        val titleTxt = String.format(context.getString(R.string.notification_title_text), highString, cityName)
        val contentTxt = String.format(context.getString(R.string.notification_content_text), description, "")
        val color = context.getColor(R.color.colorPrimary)

        val builder = NotificationCompat.Builder(context, chanelId)
                .setSmallIcon(smallIconId)
                .setLargeIcon(largeIcon)
                .setContentTitle(titleTxt)
                .setContentText(contentTxt)
                .setColor(color)
                .setAutoCancel(true)
                .setTimeoutAfter(HOUR_IN_MILLIS)
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

    @JvmStatic
    fun notifyIfNeeded(context: Context, weatherEntry: WeatherEntry) {

        val notificationsEnabled = NotifUtils.areNotificationsEnabled(context)
        val timeSinceLastNotification = NotifUtils.getEllapsedTimeSinceLastNotification(context)
        var oneDayPassedSinceLastNotification = false

        if (timeSinceLastNotification >= DateUtils.DAY_IN_MILLIS) {
            oneDayPassedSinceLastNotification = true
        }

        if (notificationsEnabled && oneDayPassedSinceLastNotification
                && !ForegroundListener.isForeground()) {
            NotifUtils.notifyUserOfNewWeather(context, weatherEntry)
        }
    }


}