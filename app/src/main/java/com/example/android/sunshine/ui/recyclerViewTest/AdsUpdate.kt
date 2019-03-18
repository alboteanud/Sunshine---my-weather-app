package com.example.android.sunshine.ui.recyclerViewTest

import com.google.android.gms.ads.AdView
import java.util.*

data class AdsUpdate(val id: Int, val date: Date, val adView: AdView?)
    : Update(id, Update.TYPE.ADS, date)