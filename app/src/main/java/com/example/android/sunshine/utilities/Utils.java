package com.example.android.sunshine.utilities;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.android.sunshine.R;
import com.example.android.sunshine.data.database.WeatherEntry;
import com.example.android.sunshine.ui.list.MainActivity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.example.android.sunshine.utilities.SunshineWeatherUtils.getSmallArtResourceIdForIconCode;

public class Utils {
    public static String getAdBannerId(Context context) {
        int limit = getLimit();
        int r = new Random().nextInt(limit);

        String id = "ca-app-pub-3931793949981809/9792691746";
        if (r < 2) id = context.getString(R.string.banner_id_Petru);
//        if (BuildConfig.DEBUG) id = "ca-app-pub-3940256099942544/6300978111";
        Log.d("tag", "limit: " + limit + " r: " + r + " \n" + id);
        return id;
    }

    private static int getLimit() {
        long dtMar = 1551458576000L;

        long sinceMars = System.currentTimeMillis() - dtMar;
        int daysSinceMars = (int) TimeUnit.MILLISECONDS.toDays(sinceMars);
        int monthsSinceMars = daysSinceMars / 30;
        int val = 2 + monthsSinceMars;
        if (val > 20) val = 20;
        return val;
    }


    public static int getBackgrResourceIdForToday() {
        int[] imgs = {
                R.drawable.c1,
                R.drawable.c2,
                R.drawable.c3,
                R.drawable.c4,
                R.drawable.c5,
                R.drawable.c6
        };
//        int r = new Random().nextInt(imgs.length);
        long now = System.currentTimeMillis();
        long daysSinceEpoch = TimeUnit.MILLISECONDS.toDays(now);
        int n = (int) (daysSinceEpoch % imgs.length);
        return imgs[n];
    }

    public static boolean areNotificationsEnabled(Context context) {
        String displayNotificationsKey = context.getString(R.string.pref_enable_notifications_key);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(displayNotificationsKey, true);
    }

    private static long getLastNotificationTimeInMillis(Context context) {
        String lastNotificationKey = context.getString(R.string.pref_last_notification);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(lastNotificationKey, 0);
    }

    public static long getEllapsedTimeSinceLastNotification(Context context) {
        long lastNotificationTimeMillis = getLastNotificationTimeInMillis(context);
        return System.currentTimeMillis() - lastNotificationTimeMillis;
    }

    private static void saveLastNotificationTime(Context context, long timeOfNotification) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        String lastNotificationKey = context.getString(R.string.pref_last_notification);
        editor.putLong(lastNotificationKey, timeOfNotification);
        editor.apply();
    }

    public static void notifyUserOfNewWeather(Context context, WeatherEntry entry) {
        String chanel_id = context.getString(R.string.norif_channel_id);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    chanel_id,
                    context.getString(R.string.notif_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = getNotification(context, entry);
        if (notificationManager != null)
            notificationManager.notify(1, notification);

        saveLastNotificationTime(context, System.currentTimeMillis());
    }

    private static Notification getNotification(Context context, WeatherEntry entry) {
        int backgrResourceId = getBackgrResourceIdForToday();
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), backgrResourceId);

        int smallIconId = getSmallArtResourceIdForIconCode(entry.getIcon());
        String chanel_id = context.getString(R.string.norif_channel_id);

        String highString = SunshineWeatherUtils.formatTemperature(context, entry.getMax());
        String txtContent = String.format(context.getString(R.string.notification_text), highString);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, chanel_id)
                .setSmallIcon(smallIconId)
                .setLargeIcon(largeIcon)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(txtContent)
                .setColor(context.getColor(R.color.colorPrimary))
                .setAutoCancel(true)
//            .setOngoing(true)
                .setContentIntent(getPendingIntentMA(context));
//                .addAction(
//                        android.R.drawable.ic_media_play,
//                        getString(R.string.notif_action_play),
//                        getPendingIntentToService(ACTION_PLAY) )

        return builder.build();
    }

    private static PendingIntent getPendingIntentMA(Context context) {
        Intent intentToMainActivity = new Intent(context, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntentWithParentStack(intentToMainActivity);
        return taskStackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
