/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version city_2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.craiovadata.android.sunshine.data.network

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.CityData.inTestMode
import com.craiovadata.android.sunshine.utilities.InjectorUtils
import com.craiovadata.android.sunshine.utilities.LogUtils.log

class SyncIntentServiceTest : IntentService("SyncIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        Log.d(LOG_TAG, "Intent service started")
        if (!inTestMode) return
        val networkDataSource =
            InjectorUtils.provideNetworkDataSource(this.applicationContext)

            val citiesIndex = intent?.getIntExtra("citiesIndex", 0) ?: 0

            val ids = CityIdsTest.cityIds2
            val cityIndexEnd = citiesIndex+ 20
            if (cityIndexEnd < ids.size) {
                networkDataSource.fetchWeatherForMultipleCitiesTest(
                    this.applicationContext,
                    ids.subList(citiesIndex, cityIndexEnd)
                )
                log("index $citiesIndex to $cityIndexEnd")
            }
    }

    companion object {
        private val LOG_TAG = SyncIntentServiceTest::class.java.simpleName
    }
}