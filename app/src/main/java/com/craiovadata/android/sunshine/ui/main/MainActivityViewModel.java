package com.craiovadata.android.sunshine.ui.main

import com.craiovadata.android.sunshine.data.RepositoryWeather
import com.craiovadata.android.sunshine.data.database.ListWeatherEntry
import com.craiovadata.android.sunshine.data.database.WeatherEntry

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainActivityViewModel internal constructor(repository: RepositoryWeather) : ViewModel() {

    val nextHoursWeather: LiveData<List<ListWeatherEntry>>
    //        Transformations.map
    val midDayWeather: LiveData<List<ListWeatherEntry>>
    //    @MainThread
    //    fun getGroupLiveData(groupId: String): LiveData<GroupDisplayOrException> {
    //
    //        var liveData = groupLiveData
    //        if (liveData == null) {
    //            val ld = repository.getGroupLiveData(groupId)
    //            liveData = Transformations.map(ld) {
    //                GroupDisplayOrException(it.data?.toGroupDisplay(), it.exception)
    //            }
    //            groupLiveData = liveData
    //        }
    //        return liveData!!
    //    }

    val allForecast: LiveData<List<WeatherEntry>>
    val currentWeather: LiveData<List<WeatherEntry>>


    init {
        nextHoursWeather = repository.nextHoursWeather
        midDayWeather = repository.midDayWeatherEntries
        allForecast = repository.allWeatherEntries
        currentWeather = repository.currentWeather
    }

}
