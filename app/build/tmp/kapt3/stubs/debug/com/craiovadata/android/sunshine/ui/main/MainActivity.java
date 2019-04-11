package com.craiovadata.android.sunshine.ui.main;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0005\u0018\u0000 \'2\u00020\u00012\u00020\u0002:\u0001\'B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0012\u0010\u001a\u001a\u00020\u00112\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0014J\b\u0010\u001d\u001a\u00020\u0011H\u0014J\b\u0010\u001e\u001a\u00020\u0011H\u0014J\b\u0010\u001f\u001a\u00020\u0011H\u0014J\b\u0010 \u001a\u00020\u0011H\u0014J\u0010\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020#H\u0002J\b\u0010$\u001a\u00020\u0011H\u0002J\b\u0010%\u001a\u00020\u0011H\u0002J\b\u0010&\u001a\u00020\u0011H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2 = {"Lcom/craiovadata/android/sunshine/ui/main/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/craiovadata/android/sunshine/ui/main/CardsAdapter$Listener;", "()V", "adView", "Lcom/google/android/gms/ads/AdView;", "adapter", "Lcom/craiovadata/android/sunshine/ui/main/CardsAdapter;", "currentWeatherEntry", "Lcom/craiovadata/android/sunshine/data/database/WeatherEntry;", "graphWeatherEntries", "", "Lcom/craiovadata/android/sunshine/data/database/ListWeatherEntry;", "handler", "Landroid/os/Handler;", "multiDayEntries", "loadAdBanner", "", "observeCurrentWeather", "viewModel", "Lcom/craiovadata/android/sunshine/ui/main/MainActivityViewModel;", "observeDaysForecastData", "observeForecastData", "onCelsiusFarClicked", "view", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onResume", "onStop", "setBackgroundDelayed", "delay", "", "showLoading", "showRecyclerView", "updateAdapter", "Companion", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity implements com.craiovadata.android.sunshine.ui.main.CardsAdapter.Listener {
    private com.google.android.gms.ads.AdView adView;
    private com.craiovadata.android.sunshine.data.database.WeatherEntry currentWeatherEntry;
    private java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> graphWeatherEntries;
    private java.util.List<com.craiovadata.android.sunshine.data.database.ListWeatherEntry> multiDayEntries;
    private final android.os.Handler handler = null;
    private com.craiovadata.android.sunshine.ui.main.CardsAdapter adapter;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TAG = "MainActivity";
    public static final com.craiovadata.android.sunshine.ui.main.MainActivity.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setBackgroundDelayed(long delay) {
    }
    
    private final void observeCurrentWeather(com.craiovadata.android.sunshine.ui.main.MainActivityViewModel viewModel) {
    }
    
    private final void observeForecastData(com.craiovadata.android.sunshine.ui.main.MainActivityViewModel viewModel) {
    }
    
    private final void observeDaysForecastData(com.craiovadata.android.sunshine.ui.main.MainActivityViewModel viewModel) {
    }
    
    private final void updateAdapter() {
    }
    
    private final void loadAdBanner() {
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
    
    private final void showRecyclerView() {
    }
    
    private final void showLoading() {
    }
    
    @java.lang.Override()
    protected void onStop() {
    }
    
    @java.lang.Override()
    public void onCelsiusFarClicked(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    public MainActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/craiovadata/android/sunshine/ui/main/MainActivity$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}