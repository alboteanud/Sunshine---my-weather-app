package com.example.android.sunshine.ui.main;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0007\u0003\u0004\u0005\u0006\u0007\b\tB\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\n"}, d2 = {"Lcom/example/android/sunshine/ui/main/CardsViewHolders;", "", "()V", "AdsViewHolder", "DetailsViewHolder", "GraphViewHolder", "MapViewHolder", "MultyDayViewHolder", "UpdateViewHolder", "WeatherViewHolder", "app_debug"})
public final class CardsViewHolders {
    
    public CardsViewHolders() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/example/android/sunshine/ui/main/CardsViewHolders$UpdateViewHolder;", "", "bindViews", "", "update", "Lcom/example/android/sunshine/ui/models/Base;", "app_debug"})
    public static abstract interface UpdateViewHolder {
        
        public abstract void bindViews(@org.jetbrains.annotations.NotNull()
        com.example.android.sunshine.ui.models.Base update);
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016\u00a8\u0006\n"}, d2 = {"Lcom/example/android/sunshine/ui/main/CardsViewHolders$WeatherViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Lcom/example/android/sunshine/ui/main/CardsViewHolders$UpdateViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "bindViews", "", "update", "Lcom/example/android/sunshine/ui/models/Base;", "app_debug"})
    public static final class WeatherViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements com.example.android.sunshine.ui.main.CardsViewHolders.UpdateViewHolder {
        
        @java.lang.Override()
        public void bindViews(@org.jetbrains.annotations.NotNull()
        com.example.android.sunshine.ui.models.Base update) {
        }
        
        public WeatherViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016\u00a8\u0006\n"}, d2 = {"Lcom/example/android/sunshine/ui/main/CardsViewHolders$DetailsViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Lcom/example/android/sunshine/ui/main/CardsViewHolders$UpdateViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "bindViews", "", "update", "Lcom/example/android/sunshine/ui/models/Base;", "app_debug"})
    public static final class DetailsViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements com.example.android.sunshine.ui.main.CardsViewHolders.UpdateViewHolder {
        
        @java.lang.Override()
        public void bindViews(@org.jetbrains.annotations.NotNull()
        com.example.android.sunshine.ui.models.Base update) {
        }
        
        public DetailsViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/example/android/sunshine/ui/main/CardsViewHolders$GraphViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Lcom/example/android/sunshine/ui/main/CardsViewHolders$UpdateViewHolder;", "itemView", "Landroid/view/View;", "listener", "Lcom/example/android/sunshine/ui/main/CardsAdapter$Listener;", "(Landroid/view/View;Lcom/example/android/sunshine/ui/main/CardsAdapter$Listener;)V", "bindViews", "", "update", "Lcom/example/android/sunshine/ui/models/Base;", "app_debug"})
    public static final class GraphViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements com.example.android.sunshine.ui.main.CardsViewHolders.UpdateViewHolder {
        private final com.example.android.sunshine.ui.main.CardsAdapter.Listener listener = null;
        
        @java.lang.Override()
        public void bindViews(@org.jetbrains.annotations.NotNull()
        com.example.android.sunshine.ui.models.Base update) {
        }
        
        public GraphViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView, @org.jetbrains.annotations.NotNull()
        com.example.android.sunshine.ui.main.CardsAdapter.Listener listener) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016\u00a8\u0006\n"}, d2 = {"Lcom/example/android/sunshine/ui/main/CardsViewHolders$MultyDayViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Lcom/example/android/sunshine/ui/main/CardsViewHolders$UpdateViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "bindViews", "", "update", "Lcom/example/android/sunshine/ui/models/Base;", "app_debug"})
    public static final class MultyDayViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements com.example.android.sunshine.ui.main.CardsViewHolders.UpdateViewHolder {
        
        @java.lang.Override()
        public void bindViews(@org.jetbrains.annotations.NotNull()
        com.example.android.sunshine.ui.models.Base update) {
        }
        
        public MultyDayViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016\u00a8\u0006\n"}, d2 = {"Lcom/example/android/sunshine/ui/main/CardsViewHolders$MapViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Lcom/example/android/sunshine/ui/main/CardsViewHolders$UpdateViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "bindViews", "", "update", "Lcom/example/android/sunshine/ui/models/Base;", "app_debug"})
    public static final class MapViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements com.example.android.sunshine.ui.main.CardsViewHolders.UpdateViewHolder {
        
        @java.lang.Override()
        public void bindViews(@org.jetbrains.annotations.NotNull()
        com.example.android.sunshine.ui.models.Base update) {
        }
        
        public MapViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016\u00a8\u0006\n"}, d2 = {"Lcom/example/android/sunshine/ui/main/CardsViewHolders$AdsViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Lcom/example/android/sunshine/ui/main/CardsViewHolders$UpdateViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "bindViews", "", "update", "Lcom/example/android/sunshine/ui/models/Base;", "app_debug"})
    public static final class AdsViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements com.example.android.sunshine.ui.main.CardsViewHolders.UpdateViewHolder {
        
        @java.lang.Override()
        public void bindViews(@org.jetbrains.annotations.NotNull()
        com.example.android.sunshine.ui.models.Base update) {
        }
        
        public AdsViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
    }
}