package com.craiovadata.android.sunshine.data.network

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.craiovadata.android.sunshine.utilities.InjectorUtils
import com.craiovadata.android.sunshine.utilities.LogUtils.addTestText
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class WebcamsWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    var latch: CountDownLatch? = null
    private val MAX_WAIT_TIME_SECONDS = 10L

    override fun doWork(): Result {
        addTestText(applicationContext, "wkWebcams")

        val defaultLatLon = 0.0
        val lat = inputData.getDouble(WEBCAMS_WORK_PARAM_LAT, defaultLatLon)
        val lon = inputData.getDouble(WEBCAMS_WORK_PARAM_LON, defaultLatLon)
        if (lat == defaultLatLon || lon == defaultLatLon) return Result.failure()

        latch = CountDownLatch(1)
        return try {
            val networkDataSource = InjectorUtils.provideNetworkDataSource(applicationContext)
//            networkDataSource.fetchWeather()
            networkDataSource.fetchWebcams(lat, lon) {success ->
                if (!success){
                    addTestText(applicationContext, "wkWebcamsRetry")
                    Result.retry()
                }
                latch?.countDown()
            }

            latch?.await(MAX_WAIT_TIME_SECONDS, TimeUnit.SECONDS)
            Result.success()
        } catch (e: Error) {
            addTestText(applicationContext, "wkWebcamsEr:$e")
//            Result.failure()
            Result.retry()
        }

    }

    companion object {
        const val WEBCAMS_WORK_PARAM_LAT = "key_wk_lat"
        const val WEBCAMS_WORK_PARAM_LON = "key_wk_lon"
    }

}