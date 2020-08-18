package com.craiovadata.android.sunshine.ui.adpterModels

import android.view.View
import com.craiovadata.android.sunshine.ui.adpterModels.Base
import com.craiovadata.android.sunshine.ui.main.CardsAdapter
import java.util.*

data class News(val queryString: String)
    : Base(-5, TYPE.NEWS, Date(0)) {

    companion object {

        @JvmStatic
        fun bindNewsToUI(itemView: View, listener: CardsAdapter.Listener) {
//            itemView.cardNews.setOnClickListener{
            itemView.setOnClickListener{
                listener.onNewsClicked(it)
            }
        }



    }

}