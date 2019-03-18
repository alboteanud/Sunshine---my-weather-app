package com.example.android.sunshine.utilities;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\nH\u0007J\u0016\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\fH\u0002\u00a8\u0006\u0016"}, d2 = {"Lcom/example/android/sunshine/utilities/NotifUtils;", "", "()V", "areNotificationsEnabled", "", "context", "Landroid/content/Context;", "buildNotif", "Landroid/app/Notification;", "entry", "Lcom/example/android/sunshine/data/database/WeatherEntry;", "getEllapsedTimeSinceLastNotification", "", "getLastNotificationTimeInMillis", "getPendingIntentMA", "Landroid/app/PendingIntent;", "notifyIfNeeded", "", "weatherEntry", "notifyUserOfNewWeather", "saveLastNotificationTime", "timeOfNotification", "app_debug"})
public final class NotifUtils {
    public static final com.example.android.sunshine.utilities.NotifUtils INSTANCE = null;
    
    public final boolean areNotificationsEnabled(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    private final long getLastNotificationTimeInMillis(android.content.Context context) {
        return 0L;
    }
    
    public final long getEllapsedTimeSinceLastNotification(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0L;
    }
    
    private final void saveLastNotificationTime(android.content.Context context, long timeOfNotification) {
    }
    
    public final void notifyUserOfNewWeather(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.example.android.sunshine.data.database.WeatherEntry entry) {
    }
    
    private final android.app.Notification buildNotif(android.content.Context context, com.example.android.sunshine.data.database.WeatherEntry entry) {
        return null;
    }
    
    private final android.app.PendingIntent getPendingIntentMA(android.content.Context context) {
        return null;
    }
    
    public static final void notifyIfNeeded(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.example.android.sunshine.data.database.WeatherEntry weatherEntry) {
    }
    
    private NotifUtils() {
        super();
    }
}