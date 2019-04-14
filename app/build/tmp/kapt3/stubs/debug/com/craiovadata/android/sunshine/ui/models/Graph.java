package com.craiovadata.android.sunshine.ui.models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005J\u0011\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u00c6\u0003J\u001b\u0010\t\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001R\u0019\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0013"}, d2 = {"Lcom/craiovadata/android/sunshine/ui/models/Graph;", "Lcom/craiovadata/android/sunshine/ui/models/Base;", "list", "", "Lcom/craiovadata/android/sunshine/data/database/ListWeatherEntry;", "(Ljava/util/List;)V", "getList", "()Ljava/util/List;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "Companion", "app_debug"})
public final class Graph extends com.craiovadata.android.sunshine.ui.models.Base {
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> list = null;
    public static final com.craiovadata.android.sunshine.ui.models.Graph.Companion Companion = null;
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> getList() {
        return null;
    }
    
    public Graph(@org.jetbrains.annotations.Nullable()
    java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> list) {
        super(null, null, null);
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.craiovadata.android.sunshine.ui.models.Graph copy(@org.jetbrains.annotations.Nullable()
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
    android.view.View view, @org.jetbrains.annotations.NotNull()
    com.craiovadata.android.sunshine.ui.main.CardsAdapter.Listener listener) {
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u001e\u0010\f\u001a\u00020\u00042\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\u000f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002\u00a8\u0006\u0010"}, d2 = {"Lcom/craiovadata/android/sunshine/ui/models/Graph$Companion;", "", "()V", "bindForecastToUI", "", "weatherEntries", "", "Lcom/craiovadata/android/sunshine/data/database/ListWeatherEntry;", "view", "Landroid/view/View;", "listener", "Lcom/craiovadata/android/sunshine/ui/main/CardsAdapter$Listener;", "drawGraph", "entries", "setLabelTime", "setTextCelsiusFarStates", "app_debug"})
    public static final class Companion {
        
        public final void bindForecastToUI(@org.jetbrains.annotations.Nullable()
        java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> weatherEntries, @org.jetbrains.annotations.NotNull()
        android.view.View view, @org.jetbrains.annotations.NotNull()
        com.craiovadata.android.sunshine.ui.main.CardsAdapter.Listener listener) {
        }
        
        private final void drawGraph(java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> entries, android.view.View view) {
        }
        
        private final void setLabelTime(android.view.View view) {
        }
        
        private final void setTextCelsiusFarStates(android.view.View view, com.craiovadata.android.sunshine.ui.main.CardsAdapter.Listener listener) {
        }
        
        private Companion() {
            super();
        }
    }
}