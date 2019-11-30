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

package com.craiovadata.android.sunshine.utilities;

import android.content.Context;

import com.craiovadata.android.sunshine.data.database.Repository;
import com.craiovadata.android.sunshine.data.database.MyDatabase;
import com.craiovadata.android.sunshine.data.network.NetworkDataSource;
import com.craiovadata.android.sunshine.ui.main.ViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for Sunshine
 */
public class InjectorUtils {

    public static Repository provideRepository(Context context) {
        MyDatabase myDatabase = MyDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.Companion.getInstance();
        NetworkDataSource networkDataSource =
                NetworkDataSource.Companion.getInstance(context.getApplicationContext(), executors);
        return Repository.Companion.getInstance(myDatabase.weatherDao(), networkDataSource, executors);
    }

    public static NetworkDataSource provideNetworkDataSource(Context context) {
        // This call to provide repository is necessary if the app starts from a service - in this
        // case the repository will not exist unless it is specifically created.
        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.Companion.getInstance();
        return NetworkDataSource.Companion.getInstance(context.getApplicationContext(), executors);
    }

//    public static DetailViewModelFactory provideDetailViewModelFactory(Context context, Date date) {
//        RepositoryWeather repository = provideRepository(context.getApplicationContext());
//        return new DetailViewModelFactory(repository, date);
//    }

    public static ViewModelFactory provideMainActivityViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new ViewModelFactory(repository);
    }

}