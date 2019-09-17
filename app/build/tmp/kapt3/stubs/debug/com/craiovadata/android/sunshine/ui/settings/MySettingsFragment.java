package com.craiovadata.android.sunshine.ui.settings;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\b\u0010\u0007\u001a\u00020\u0004H\u0002J\u001c\u0010\b\u001a\u00020\u00042\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\fH\u0002\u00a8\u0006\u0010"}, d2 = {"Lcom/craiovadata/android/sunshine/ui/settings/MySettingsFragment;", "Landroidx/preference/PreferenceFragmentCompat;", "()V", "bindPreferenceSummaryToValue", "", "preference", "Landroidx/preference/Preference;", "clearFeedbackPref", "onCreatePreferences", "savedInstanceState", "Landroid/os/Bundle;", "rootKey", "", "sendFeedbackToFirebase", "stringValue", "Companion", "app_debug"})
public final class MySettingsFragment extends androidx.preference.PreferenceFragmentCompat {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "MySettingsFragment";
    private static final androidx.preference.Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = null;
    public static final com.craiovadata.android.sunshine.ui.settings.MySettingsFragment.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public void onCreatePreferences(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState, @org.jetbrains.annotations.Nullable()
    java.lang.String rootKey) {
    }
    
    private final void sendFeedbackToFirebase(java.lang.String stringValue) {
    }
    
    private final void clearFeedbackPref() {
    }
    
    private final void bindPreferenceSummaryToValue(androidx.preference.Preference preference) {
    }
    
    public MySettingsFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/craiovadata/android/sunshine/ui/settings/MySettingsFragment$Companion;", "", "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "sBindPreferenceSummaryToValueListener", "Landroidx/preference/Preference$OnPreferenceChangeListener;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getTAG() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}