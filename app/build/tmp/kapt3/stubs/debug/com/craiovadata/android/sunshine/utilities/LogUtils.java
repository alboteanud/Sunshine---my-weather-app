package com.craiovadata.android.sunshine.utilities;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bH\u0007J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000bH\u0007J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000bH\u0007J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/craiovadata/android/sunshine/utilities/LogUtils;", "", "()V", "logDBvalues", "", "context", "Landroid/content/Context;", "forecastEntries", "", "Lcom/craiovadata/android/sunshine/data/database/ListWeatherEntry;", "cwEntries", "Lcom/craiovadata/android/sunshine/data/database/WeatherEntry;", "logEntry", "entry", "logResponse", "logTag", "", "saveDateToPrefs", "app_debug"})
public final class LogUtils {
    public static final com.craiovadata.android.sunshine.utilities.LogUtils INSTANCE = null;
    
    public static final void saveDateToPrefs(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public static final void logResponse(@org.jetbrains.annotations.NotNull()
    java.lang.String logTag, @org.jetbrains.annotations.NotNull()
    com.craiovadata.android.sunshine.data.database.WeatherEntry entry) {
    }
    
    public static final void logEntry(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.craiovadata.android.sunshine.data.database.WeatherEntry entry) {
    }
    
    public static final void logDBvalues(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> forecastEntries, @org.jetbrains.annotations.NotNull()
    java.util.List<com.craiovadata.android.sunshine.data.database.WeatherEntry> cwEntries) {
    }
    
    private LogUtils() {
        super();
    }
}