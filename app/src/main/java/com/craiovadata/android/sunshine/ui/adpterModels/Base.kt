package com.craiovadata.android.sunshine.ui.adpterModels

import com.craiovadata.android.sunshine.ui.adpterModels.Base.TYPE.Companion.DAYS
import java.util.*

abstract class Base(
//        var _id: Long? = -1,
        var _type: String? = DAYS

) {

    class TYPE {
        companion object {
            const val WEATHER = "weather"
            const val DETAILS = "details"
            const val GRAPH = "graph"
            const val MAP = "map"
            const val WEBCAM = "webcam"
            const val NEWS = "news"
            const val ADS = "ads"
            const val DAYS: String = "days"
        }
    }

}