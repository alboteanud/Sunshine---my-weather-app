package com.craiovadata.android.sunshine.utilities

import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner

class DummyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get()
                .lifecycle
                .addObserver(ForegroundListener())
    }



}