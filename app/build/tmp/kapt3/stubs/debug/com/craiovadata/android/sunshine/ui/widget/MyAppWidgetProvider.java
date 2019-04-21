package com.craiovadata.android.sunshine.ui.widget;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J(\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0002\u00a8\u0006\u000e"}, d2 = {"Lcom/craiovadata/android/sunshine/ui/widget/MyAppWidgetProvider;", "Landroid/appwidget/AppWidgetProvider;", "()V", "onUpdate", "", "context", "Landroid/content/Context;", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetIds", "", "updateWidgets", "currentWeather", "Lcom/craiovadata/android/sunshine/data/database/WeatherEntry;", "app_debug"})
public final class MyAppWidgetProvider extends android.appwidget.AppWidgetProvider {
    
    @java.lang.Override()
    public void onUpdate(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.appwidget.AppWidgetManager appWidgetManager, @org.jetbrains.annotations.NotNull()
    int[] appWidgetIds) {
    }
    
    private final void updateWidgets(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int[] appWidgetIds, com.craiovadata.android.sunshine.data.database.WeatherEntry currentWeather) {
    }
    
    public MyAppWidgetProvider() {
        super();
    }
}