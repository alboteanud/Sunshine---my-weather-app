package com.example.android.sunshine.ui.detail;

import java.lang.System;

/**
 * * Displays single day's forecast
 */
@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u000e"}, d2 = {"Lcom/example/android/sunshine/ui/detail/DetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "mViewModel", "Lcom/example/android/sunshine/ui/detail/DetailActivityViewModel;", "getMViewModel", "()Lcom/example/android/sunshine/ui/detail/DetailActivityViewModel;", "setMViewModel", "(Lcom/example/android/sunshine/ui/detail/DetailActivityViewModel;)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "app_debug"})
public final class DetailActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull()
    public com.example.android.sunshine.ui.detail.DetailActivityViewModel mViewModel;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String WEATHER_ID_EXTRA = "WEATHER_ID_EXTRA";
    public static final com.example.android.sunshine.ui.detail.DetailActivity.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.android.sunshine.ui.detail.DetailActivityViewModel getMViewModel() {
        return null;
    }
    
    public final void setMViewModel(@org.jetbrains.annotations.NotNull()
    com.example.android.sunshine.ui.detail.DetailActivityViewModel p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public DetailActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/android/sunshine/ui/detail/DetailActivity$Companion;", "", "()V", "WEATHER_ID_EXTRA", "", "getWEATHER_ID_EXTRA", "()Ljava/lang/String;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getWEATHER_ID_EXTRA() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}