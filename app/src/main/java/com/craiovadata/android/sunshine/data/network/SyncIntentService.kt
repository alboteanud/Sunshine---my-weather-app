
package com.craiovadata.android.sunshine.data.network

import android.app.IntentService
import android.app.Notification
import android.content.Intent
import android.util.Log
import com.craiovadata.android.sunshine.utilities.InjectorUtils
import com.craiovadata.android.sunshine.utilities.NotifUtils

/**
 * An [IntentService] subclass for immediately scheduling a sync with the server off of the
 * main thread. This is necessary because SimpleWorker Manager
 * will not trigger a job immediately. This should only be called when the application is on the
 * screen.
 */
class SyncIntentService : IntentService("SyncIntentService") {

    override fun onCreate() {
        super.onCreate()
        val notif = NotifUtils.getUpdatingNotification(this)
        startForeground(101, notif)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(LOG_TAG, "Intent service started")
        val networkDataSource =
            InjectorUtils.provideNetworkDataSource(this.applicationContext)
            networkDataSource.fetchWeather()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    companion object {
        private val LOG_TAG = SyncIntentService::class.java.simpleName
    }
}