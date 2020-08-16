
package com.craiovadata.android.sunshine.utilities

import android.content.Context
import com.craiovadata.android.sunshine.data.database.MyDatabase
import com.craiovadata.android.sunshine.data.database.Repository
import com.craiovadata.android.sunshine.data.network.NetworkDataSource
import com.craiovadata.android.sunshine.ui.main.ViewModelFactory

/**
 * Provides static methods to inject the various classes needed for Sunshine
 */
object InjectorUtils {

    fun provideRepository(context: Context): Repository {
        val myDatabase = MyDatabase.getInstance(context.applicationContext)
        val executors = AppExecutors.instance
        val networkDataSource =
            NetworkDataSource.getInstance(context.applicationContext, executors)
        //        NetworkDataSource.addTestText(context, "prRep")
        return Repository.getInstance(
            myDatabase.weatherDao()!!,
            networkDataSource,
            executors)

    }

    fun provideNetworkDataSource(context: Context): NetworkDataSource {
        // This call to provide repository is necessary if the app starts from a service - in this
// case the repository will not exist unless it is specifically created.
        provideRepository(context.applicationContext)
        val executors = AppExecutors.instance
        val networkDataSource = NetworkDataSource.getInstance(context.applicationContext, executors)
//        NetworkDataSource.addTestText(context, "prNDS")
        return networkDataSource
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