package com.craiovadata.android.sunshine.ui.models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0015\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0012"}, d2 = {"Lcom/craiovadata/android/sunshine/ui/models/Map;", "Lcom/craiovadata/android/sunshine/ui/models/Base;", "weatherEntry", "Lcom/craiovadata/android/sunshine/data/database/WeatherEntry;", "(Lcom/craiovadata/android/sunshine/data/database/WeatherEntry;)V", "getWeatherEntry", "()Lcom/craiovadata/android/sunshine/data/database/WeatherEntry;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "Companion", "app_debug"})
public final class Map extends com.craiovadata.android.sunshine.ui.models.Base {
    @org.jetbrains.annotations.Nullable()
    private final com.craiovadata.android.sunshine.data.database.WeatherEntry weatherEntry = null;
    private static final java.lang.String BASE_STATIC_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap?";
    public static final com.craiovadata.android.sunshine.ui.models.Map.Companion Companion = null;
    
    @org.jetbrains.annotations.Nullable()
    public final com.craiovadata.android.sunshine.data.database.WeatherEntry getWeatherEntry() {
        return null;
    }
    
    public Map(@org.jetbrains.annotations.Nullable()
    com.craiovadata.android.sunshine.data.database.WeatherEntry weatherEntry) {
        super(null, null, null);
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.craiovadata.android.sunshine.data.database.WeatherEntry component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.craiovadata.android.sunshine.ui.models.Map copy(@org.jetbrains.annotations.Nullable()
    com.craiovadata.android.sunshine.data.database.WeatherEntry weatherEntry) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object p0) {
        return false;
    }
    
    public static final void bindMapToUI(@org.jetbrains.annotations.Nullable()
    com.craiovadata.android.sunshine.data.database.WeatherEntry weatherEntry, @org.jetbrains.annotations.NotNull()
    android.view.View itemView) {
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0007J(\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0016\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/craiovadata/android/sunshine/ui/models/Map$Companion;", "", "()V", "BASE_STATIC_MAP_URL", "", "bindMapToUI", "", "weatherEntry", "Lcom/craiovadata/android/sunshine/data/database/WeatherEntry;", "itemView", "Landroid/view/View;", "buildUrlGoogleStaticMap", "context", "Landroid/content/Context;", "lat", "", "lon", "zoomLevel", "", "loadMap", "url", "mapImageView", "Landroid/widget/ImageView;", "app_debug"})
    public static final class Companion {
        
        public final void bindMapToUI(@org.jetbrains.annotations.Nullable()
        com.craiovadata.android.sunshine.data.database.WeatherEntry weatherEntry, @org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
        }
        
        public final void loadMap(@org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.NotNull()
        android.widget.ImageView mapImageView) {
        }
        
        private final java.lang.String buildUrlGoogleStaticMap(android.content.Context context, double lat, double lon, int zoomLevel) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}