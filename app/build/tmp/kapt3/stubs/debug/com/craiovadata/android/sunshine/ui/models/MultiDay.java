package com.craiovadata.android.sunshine.ui.models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005J\u0011\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u00c6\u0003J\u001b\u0010\t\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001R\u0019\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0013"}, d2 = {"Lcom/craiovadata/android/sunshine/ui/models/MultiDay;", "Lcom/craiovadata/android/sunshine/ui/models/Base;", "list", "", "Lcom/craiovadata/android/sunshine/data/database/ListWeatherEntry;", "(Ljava/util/List;)V", "getList", "()Ljava/util/List;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "Companion", "app_debug"})
public final class MultiDay extends com.craiovadata.android.sunshine.ui.models.Base {
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> list = null;
    public static final com.craiovadata.android.sunshine.ui.models.MultiDay.Companion Companion = null;
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> getList() {
        return null;
    }
    
    public MultiDay(@org.jetbrains.annotations.Nullable()
    java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> list) {
        super(null, null, null);
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.craiovadata.android.sunshine.ui.models.MultiDay copy(@org.jetbrains.annotations.Nullable()
    java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> list) {
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
    
    public static final void bindForecastToUI(@org.jetbrains.annotations.Nullable()
    java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> weatherEntries, @org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J \u0010\t\u001a\u00020\u00042\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\bH\u0007\u00a8\u0006\r"}, d2 = {"Lcom/craiovadata/android/sunshine/ui/models/MultiDay$Companion;", "", "()V", "bindDayToUI", "", "entry", "Lcom/craiovadata/android/sunshine/data/database/ListWeatherEntry;", "dayView", "Landroid/view/View;", "bindForecastToUI", "weatherEntries", "", "view", "app_debug"})
    public static final class Companion {
        
        public final void bindForecastToUI(@org.jetbrains.annotations.Nullable()
        java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> weatherEntries, @org.jetbrains.annotations.NotNull()
        android.view.View view) {
        }
        
        private final void bindDayToUI(com.craiovadata.android.sunshine.data.database.ListWeatherEntry entry, android.view.View dayView) {
        }
        
        private Companion() {
            super();
        }
    }
}