package com.example.android.sunshine.ui.update_models

import com.google.android.gms.ads.AdView
import java.util.*

data class AdBannerUpdate(val adView: AdView)
    : BaseUpdate(adView.id, BaseUpdate.TYPE.ADS, Date(0))