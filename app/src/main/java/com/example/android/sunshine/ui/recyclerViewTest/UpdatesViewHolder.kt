package com.example.android.sunshine.ui.recyclerViewTest

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class UpdatesViewHolder {

    interface UpdateViewHolder {
        fun bindViews(update: Update)
    }

// other view holders...

    class ReviewViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), UpdateViewHolder {

        // get the views reference from itemView...

        override fun bindViews(update: Update) {
            val mUpdate = update as ReviewUpdate

            // bind update values to views
        }
    }

}