package com.example.android.sunshine.ui.cards

import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.ads_card.view.*


object AdsCardUtils {

    fun bindAdsToUI(itemView: View, adView: AdView?) {
//            val bannerHolder = holder as AdViewHolder
//            val adView = recyclerViewItems.get(position) as AdView
//            val adCardView = bannerHolder.itemView as ViewGroup
        // The AdViewHolder recycled by the RecyclerView may be a different
        // instance than the one used previously for this position. Clear the
        // AdViewHolder of any subviews in case it has a different
        // AdView associated with it, and make sure the AdView for this position doesn't
        // already have a parent of a different recycled AdViewHolder.
        val container = itemView.adsContainer
        if (container.childCount > 0) {
            container.removeAllViews()
        }
        if (adView?.parent != null) {
            (adView.parent as ViewGroup).removeView(adView)
        }
        container.addView(adView)

    }
}
