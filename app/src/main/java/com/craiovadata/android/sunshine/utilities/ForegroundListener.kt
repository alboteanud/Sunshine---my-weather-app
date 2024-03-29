package com.craiovadata.android.sunshine.utilities

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class ForegroundListener : LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.v("ProcessLog", "APP IS ON FOREGROUND")
        active = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.v("ProcessLog", "APP IS IN BACKGROUND")

        active = false
    }


    companion object {
        var active = false
        fun isForeground(): Boolean { return active
        }
         fun isBackground(): Boolean { return !active
        }

    }

}