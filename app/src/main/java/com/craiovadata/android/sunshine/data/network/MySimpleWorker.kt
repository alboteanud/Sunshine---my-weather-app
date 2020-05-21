package com.craiovadata.android.sunshine.data.network

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.craiovadata.android.sunshine.CityData.inTestMode
import com.craiovadata.android.sunshine.data.network.NetworkDataSource.Companion.addTestText
import com.craiovadata.android.sunshine.ui.main.MainActivity
import com.craiovadata.android.sunshine.utilities.InjectorUtils
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat


class MySimpleWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        return try {
            val networkDataSource = InjectorUtils.provideNetworkDataSource(applicationContext)
            val didFetch = networkDataSource.fetchWeather()
//            addTestText(applicationContext, "wk-$didFetch")
            if (didFetch) Result.success()
            else Result.retry()
        } catch (e: Error) {
            addTestText(applicationContext, "wk-err")
            Result.failure()
        }
    }

    override fun onStopped() {
        super.onStopped()
    }
}