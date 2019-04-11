package com.craiovadata.android.sunshine.utilities;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\b\u0010\u0005\u001a\u00020\u0004H\u0007\u00a8\u0006\u0007"}, d2 = {"Lcom/craiovadata/android/sunshine/utilities/ForegroundListener;", "Landroidx/lifecycle/LifecycleObserver;", "()V", "onStart", "", "onStop", "Companion", "app_debug"})
public final class ForegroundListener implements androidx.lifecycle.LifecycleObserver {
    private static boolean active;
    public static final com.craiovadata.android.sunshine.utilities.ForegroundListener.Companion Companion = null;
    
    @androidx.lifecycle.OnLifecycleEvent(value = androidx.lifecycle.Lifecycle.Event.ON_START)
    public final void onStart() {
    }
    
    @androidx.lifecycle.OnLifecycleEvent(value = androidx.lifecycle.Lifecycle.Event.ON_STOP)
    public final void onStop() {
    }
    
    public ForegroundListener() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 13}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\n"}, d2 = {"Lcom/craiovadata/android/sunshine/utilities/ForegroundListener$Companion;", "", "()V", "active", "", "getActive", "()Z", "setActive", "(Z)V", "isForeground", "app_debug"})
    public static final class Companion {
        
        public final boolean getActive() {
            return false;
        }
        
        public final void setActive(boolean p0) {
        }
        
        public final boolean isForeground() {
            return false;
        }
        
        private Companion() {
            super();
        }
    }
}