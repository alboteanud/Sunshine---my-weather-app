
package com.craiovadata.android.sunshine.data.network

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.craiovadata.android.sunshine.utilities.InjectorUtils

/**
 * An [IntentService] subclass for immediately scheduling a sync with the server off of the
 * main thread. This is necessary because SimpleWorker Manager
 * will not trigger a job immediately. This should only be called when the application is on the
 * screen.
 */
class SyncIntentService : IntentService("SyncIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        Log.d(LOG_TAG, "Intent service started")
//        NetworkDataSource.addTestText(applicationContext, "syInt")
        val networkDataSource =
            InjectorUtils.provideNetworkDataSource(this.applicationContext)
//            networkDataSource.fetchWeather()
            networkDataSource.fetchWeatheMainThread {

            }
    }

    companion object {
        private val LOG_TAG = SyncIntentService::class.java.simpleName
    }
}