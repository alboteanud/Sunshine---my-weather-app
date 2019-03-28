package com.craiovadata.android.sunshine.ui.models

import com.craiovadata.android.sunshine.ui.models.Base.TYPE.Companion.DAYS
import java.util.*

abstract class Base(
        var _id: Int? = -1,
        var _type: String? = DAYS,
        var _date: Date? = Date(0)
) {

    class TYPE {
        companion object {
            const val WEATHER = "weather"
            const val DETAILS = "details"
            const val GRAPH = "graph"
            const val MAP = "map"
            const val ADS = "ads"
            const val DAYS: String = "days"
        }
    }

}