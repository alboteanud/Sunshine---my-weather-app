package com.craiovadata.android.sunshine.data.network

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.craiovadata.android.sunshine.data.network.NetworkDataSource.Companion.addTestText
import com.craiovadata.android.sunshine.utilities.InjectorUtils


class MyWork(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        addTestText(applicationContext, "wkStart")

        return try {
            val networkDataSource = InjectorUtils.provideNetworkDataSource(applicationContext)
            networkDataSource.fetchWeatherMainThread()
//            addTestText(applicationContext, "wkFW")
            Result.success()
        } catch (e: Error) {
            addTestText(applicationContext, "wkErr")
            Result.retry()
        }
    }
}