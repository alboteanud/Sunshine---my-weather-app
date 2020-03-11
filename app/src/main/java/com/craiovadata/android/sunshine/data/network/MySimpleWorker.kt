package com.craiovadata.android.sunshine.data.network

import android.content.Context;
import android.util.Log
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.craiovadata.android.sunshine.utilities.InjectorUtils


class MySimpleWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        Log.d("MySimpleWorker", "Job service started")
        val networkDataSource =
            InjectorUtils.provideNetworkDataSource(this.applicationContext)
            networkDataSource.fetchWeather()
           return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
//        TODO("Cleanup, because you are being stopped")
    }
}