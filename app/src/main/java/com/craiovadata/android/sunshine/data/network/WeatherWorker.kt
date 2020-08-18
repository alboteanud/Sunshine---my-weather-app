package com.craiovadata.android.sunshine.data.network

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.craiovadata.android.sunshine.utilities.InjectorUtils
import com.craiovadata.android.sunshine.utilities.LogUtils.addTestText
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class WeatherWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    var latch: CountDownLatch? = null
    private val MAX_WAIT_TIME_SECONDS = 10L

    override fun doWork(): Result {
        addTestText(applicationContext, "wk")
        latch = CountDownLatch(1)

        return try {
            val networkDataSource = InjectorUtils.provideNetworkDataSource(applicationContext)
//            networkDataSource.fetchWeather()
            networkDataSource.fetchWeather {weatherEntry ->
                if (weatherEntry==null){
                    addTestText(applicationContext, "fetchWeaErr_wkRetry")
                    Result.retry()
                }
                latch?.countDown()
            }

            latch?.await(MAX_WAIT_TIME_SECONDS, TimeUnit.SECONDS)
            Result.success()
        } catch (e: Error) {
            addTestText(applicationContext, "wkEr:$e")
//            Result.failure()
            Result.retry()
        }

    }

}