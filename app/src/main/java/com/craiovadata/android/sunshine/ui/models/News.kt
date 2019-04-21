package com.craiovadata.android.sunshine.ui.models

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import com.craiovadata.android.sunshine.ui.main.CardsAdapter
import kotlinx.android.synthetic.main.news_card.view.*
import java.util.*

data class News(val queryString: String)
    : Base(-5, Base.TYPE.NEWS, Date(0)) {

    companion object {

        @JvmStatic
        fun bindNewsToUI(itemView: View, listener: CardsAdapter.Listener) {

            itemView.cardNews.setOnClickListener{
                listener.onNewsClicked(it)
            }
        }



    }

}