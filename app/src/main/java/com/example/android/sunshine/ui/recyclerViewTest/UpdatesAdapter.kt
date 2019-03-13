package com.example.android.sunshine.ui.recyclerViewTest

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.sunshine.R

class UpdatesAdapter(val context: Context,
                     var updatesList: List<Update>,
                     val listener: onItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_FRIEND = 0
        const val TYPE_REVIEW = 1
    }

    override fun getItemViewType(position: Int): Int {
        val type = when (updatesList[position].updateType) {
            Update.TYPE.REVIEW -> TYPE_REVIEW
            // other types...
            else -> TYPE_FRIEND
        }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder = when (viewType) {
            TYPE_FRIEND -> FriendUpdateViewHolder(parent.inflate(R.layout.update_friend), listener.onFriendNameOrImageClicked())
            // other view holders...
            else -> UpdatesViewHolder.ReviewViewHolder(parent.inflate(R.layout.udpate_reviews), listener.onUserNameOrImageClicked())
        }
        return viewHolder
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UpdatesViewHolder.UpdateViewHolder).bindViews(updatesList[position])
    }

    override fun getItemCount() = updatesList.size

    fun setUpdates(updates: List<Update>) {
        updatesList = updates
        notifyDataSetChanged()
    }

    interface onItemClickListener {
        fun onUserNameOrImageClicked()
        fun onFriendNameOrImageClicked()
        // other listeners...
    }
}