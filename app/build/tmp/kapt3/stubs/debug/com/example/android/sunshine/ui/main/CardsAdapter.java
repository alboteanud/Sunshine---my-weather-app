package com.example.android.sunshine.ui.main;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u001e2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u001e\u001fB#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0012H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0012H\u0016J\u0018\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0012H\u0016J\u0014\u0010\u001c\u001a\u00020\u00162\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/example/android/sunshine/ui/main/CardsAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "context", "Landroid/content/Context;", "listUpdates", "", "Lcom/example/android/sunshine/ui/models/Base;", "listener", "Lcom/example/android/sunshine/ui/main/CardsAdapter$Listener;", "(Landroid/content/Context;Ljava/util/List;Lcom/example/android/sunshine/ui/main/CardsAdapter$Listener;)V", "getContext", "()Landroid/content/Context;", "getListUpdates", "()Ljava/util/List;", "setListUpdates", "(Ljava/util/List;)V", "getItemCount", "", "getItemViewType", "position", "onBindViewHolder", "", "holder", "onCreateViewHolder", "viewGroup", "Landroid/view/ViewGroup;", "viewType", "setUpdates", "newUpdates", "Companion", "Listener", "app_debug"})
public final class CardsAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<? extends com.example.android.sunshine.ui.models.Base> listUpdates;
    private final com.example.android.sunshine.ui.main.CardsAdapter.Listener listener = null;
    public static final int VIEW_TYPE_WEATHER = 0;
    public static final int VIEW_TYPE_GRAPH = 1;
    public static final int VIEW_TYPE_DETAILS = 2;
    public static final int VIEW_TYPE_MAP = 3;
    public static final int VIEW_TYPE_ADS = 4;
    public static final int VIEW_TYPE_DAYS = 5;
    public static final com.example.android.sunshine.ui.main.CardsAdapter.Companion Companion = null;
    
    @java.lang.Override()
    public int getItemViewType(int position) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup viewGroup, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    /**
     * * Swaps the list used by the ForecastAdapter for its weather data. This method is called by
     *     * [MainActivity] after a load has finished. When this method is called, we assume we have
     *     * a new set of data, so we call notifyDataSetChanged to tell the RecyclerView to update.
     *     *
     *     * @param newUpdates the new list of forecasts to use as ForecastAdapter's data source
     */
    public final void setUpdates(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.example.android.sunshine.ui.models.Base> newUpdates) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.android.sunshine.ui.models.Base> getListUpdates() {
        return null;
    }
    
    public final void setListUpdates(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.example.android.sunshine.ui.models.Base> p0) {
    }
    
    public CardsAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.example.android.sunshine.ui.models.Base> listUpdates, @org.jetbrains.annotations.NotNull()
    com.example.android.sunshine.ui.main.CardsAdapter.Listener listener) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/example/android/sunshine/ui/main/CardsAdapter$Listener;", "", "onCelsiusFarClicked", "", "view", "Landroid/view/View;", "app_debug"})
    public static abstract interface Listener {
        
        public abstract void onCelsiusFarClicked(@org.jetbrains.annotations.NotNull()
        android.view.View view);
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/example/android/sunshine/ui/main/CardsAdapter$Companion;", "", "()V", "VIEW_TYPE_ADS", "", "VIEW_TYPE_DAYS", "VIEW_TYPE_DETAILS", "VIEW_TYPE_GRAPH", "VIEW_TYPE_MAP", "VIEW_TYPE_WEATHER", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}