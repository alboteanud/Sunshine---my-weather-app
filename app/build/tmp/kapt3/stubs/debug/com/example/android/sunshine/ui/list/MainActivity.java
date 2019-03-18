package com.example.android.sunshine.ui.list;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 %2\u00020\u00012\u00020\u0002:\u0001%B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0012\u0010\u0018\u001a\u00020\u00152\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\b\u0010\u001b\u001a\u00020\u0015H\u0014J\b\u0010\u001c\u001a\u00020\u0015H\u0014J\b\u0010\u001d\u001a\u00020\u0015H\u0014J\b\u0010\u001e\u001a\u00020\u0015H\u0014J\b\u0010\u001f\u001a\u00020\u0015H\u0014J\b\u0010 \u001a\u00020\u0015H\u0016J\b\u0010!\u001a\u00020\u0015H\u0002J\b\u0010\"\u001a\u00020\u0015H\u0002J\b\u0010#\u001a\u00020\u0015H\u0002J\u0010\u0010$\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/example/android/sunshine/ui/list/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/example/android/sunshine/ui/recyclerViewTest/Adapter$onItemClickListener;", "()V", "adView", "Lcom/google/android/gms/ads/AdView;", "adsUpdate", "Lcom/example/android/sunshine/ui/recyclerViewTest/AdsUpdate;", "currentWeatherUpdate", "Lcom/example/android/sunshine/ui/recyclerViewTest/CurrentWeatherUpdate;", "detailsUpdate", "Lcom/example/android/sunshine/ui/recyclerViewTest/DetailsUpdate;", "graphForecastUpdate", "Lcom/example/android/sunshine/ui/recyclerViewTest/GraphForecastUpdate;", "mPosition", "", "mapUpdate", "Lcom/example/android/sunshine/ui/recyclerViewTest/MapUpdate;", "viewModel", "Lcom/example/android/sunshine/ui/list/MainActivityViewModel;", "initAdBanner", "", "adapter", "Lcom/example/android/sunshine/ui/recyclerViewTest/Adapter;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onResume", "onStart", "onStop", "onWeatherImageClicked", "setBackgroundImg", "showLoading", "showRecyclerView", "updateAdapter", "Companion", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity implements com.example.android.sunshine.ui.recyclerViewTest.Adapter.onItemClickListener {
    private com.example.android.sunshine.ui.list.MainActivityViewModel viewModel;
    private int mPosition;
    private com.example.android.sunshine.ui.recyclerViewTest.CurrentWeatherUpdate currentWeatherUpdate;
    private com.example.android.sunshine.ui.recyclerViewTest.DetailsUpdate detailsUpdate;
    private com.example.android.sunshine.ui.recyclerViewTest.GraphForecastUpdate graphForecastUpdate;
    private com.example.android.sunshine.ui.recyclerViewTest.MapUpdate mapUpdate;
    private com.example.android.sunshine.ui.recyclerViewTest.AdsUpdate adsUpdate;
    private com.google.android.gms.ads.AdView adView;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TAG = "MainActivity";
    public static final com.example.android.sunshine.ui.list.MainActivity.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setBackgroundImg() {
    }
    
    private final void initAdBanner(com.example.android.sunshine.ui.recyclerViewTest.Adapter adapter) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    private final void updateAdapter(com.example.android.sunshine.ui.recyclerViewTest.Adapter adapter) {
    }
    
    private final void showRecyclerView() {
    }
    
    private final void showLoading() {
    }
    
    @java.lang.Override()
    protected void onStart() {
    }
    
    @java.lang.Override()
    protected void onStop() {
    }
    
    @java.lang.Override()
    public void onWeatherImageClicked() {
    }
    
    public MainActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/android/sunshine/ui/list/MainActivity$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}