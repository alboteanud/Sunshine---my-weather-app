package com.example.android.sunshine.ui.recyclerViewTest

import java.util.*

data class MapUpdate
(val date_: Date, val lat: Double, val long: Double)
    : Update(Update.TYPE.MAP)