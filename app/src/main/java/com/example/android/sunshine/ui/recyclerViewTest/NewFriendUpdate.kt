package com.example.android.sunshine.ui.recyclerViewTest

data class NewFriendUpdate(
        val user: User,
        val updatedAt: String,
        val newFriendImageUrl: String,
        val newFriendName: String
) : Update(Update.TYPE.NEW_FRIEND, user, updatedAt)