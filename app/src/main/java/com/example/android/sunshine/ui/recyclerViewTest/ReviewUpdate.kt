package com.example.android.sunshine.ui.recyclerViewTest

data class ReviewUpdate(
        val user: User,
        val updatedAt: String,
        val rating: String
//        val book: Book
) : Update(Update.TYPE.REVIEW, user, updatedAt)