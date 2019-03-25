package com.example.android.sunshine.utilities;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\b\u0010\n\u001a\u00020\u000bH\u0002J\u0006\u0010\f\u001a\u00020\u000bJ\b\u0010\r\u001a\u00020\u000eH\u0007J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0007J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0004H\u0007J*\u0010\u0014\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\t2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/example/android/sunshine/utilities/Utils;", "", "()V", "TIME_ZONE", "", "images", "", "getAdBannerId", "context", "Landroid/content/Context;", "getAdDaysLimit", "", "getBackResId", "getCityOffset", "", "getCityTimeZone", "Ljava/util/TimeZone;", "getFormatterCityTZ", "Ljava/text/SimpleDateFormat;", "pattern", "logDBvalues", "", "forecastEntries", "", "Lcom/example/android/sunshine/data/database/ListWeatherEntry;", "cwEntries", "Lcom/example/android/sunshine/data/database/WeatherEntry;", "app_debug"})
public final class Utils {
    private static final java.lang.String TIME_ZONE = "Australia/Sydney";
    private static final int[] images = null;
    public static final com.example.android.sunshine.utilities.Utils INSTANCE = null;
    
    @org.jetbrains.annotations.Nullable()
    public static final java.util.TimeZone getCityTimeZone() {
        return null;
    }
    
    public static final long getCityOffset() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.text.SimpleDateFormat getFormatterCityTZ(@org.jetbrains.annotations.NotNull()
    java.lang.String pattern) {
        return null;
    }
    
    private final int getAdDaysLimit() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAdBannerId(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    public final int getBackResId() {
        return 0;
    }
    
    public final void logDBvalues(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.android.sunshine.data.database.ListWeatherEntry> forecastEntries, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.android.sunshine.data.database.WeatherEntry> cwEntries) {
    }
    
    private Utils() {
        super();
    }
}