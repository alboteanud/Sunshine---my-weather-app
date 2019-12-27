/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version c2.0 (the "License");
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
package com.craiovadata.android.sunshine.utilities

import android.content.Context
import com.craiovadata.android.sunshine.data.database.MyDatabase.Companion.getInstance
import com.craiovadata.android.sunshine.data.database.Repository
import com.craiovadata.android.sunshine.data.database.Repository.Companion.getInstance
import com.craiovadata.android.sunshine.data.network.NetworkDataSource
import com.craiovadata.android.sunshine.data.network.NetworkDataSource.Companion.getInstance
import com.craiovadata.android.sunshine.ui.main.ViewModelFactory
import com.craiovadata.android.sunshine.utilities.AppExecutors.Companion.instance

/**
 * Provides static methods to inject the various classes needed for Sunshine
 */
object InjectorUtils {
    fun provideRepository(context: Context): Repository {
        val myDatabase = getInstance(context.applicationContext)
        val executors = instance
        val networkDataSource =
            getInstance(context.applicationContext, executors)
        return getInstance(
            myDatabase!!.weatherDao()!!,
            networkDataSource,
            executors
        )
    }

    fun provideNetworkDataSource(context: Context): NetworkDataSource { // This call to provide repository is necessary if the app starts from a service - in this
// case the repository will not exist unless it is specifically created.
        provideRepository(context.applicationContext)
        val executors = instance
        return getInstance(context.applicationContext, executors)
    }

    //    public static DetailViewModelFactory provideDetailViewModelFactory(Context context, Date date) {
//        RepositoryWeather repository = provideRepository(context.getApplicationContext());
//        return new DetailViewModelFactory(repository, date);
//    }
    fun provideMainActivityViewModelFactory(context: Context): ViewModelFactory {
        val repository =
            provideRepository(context.applicationContext)
        return ViewModelFactory(repository)
    }
}