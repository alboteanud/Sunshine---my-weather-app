package com.example.android.sunshine.ui.recyclerViewTest

abstract class Update(
        val updateType: String,
        val updateUser: User,
        val updateTime: String) {

    class TYPE {
        companion object {
            val NEW_FRIEND = "friend"
            val COMMENT = "comment"
            val READ_STATUS = "readstatus"
            val USER_STATUS = "userstatus"
            val REVIEW = "review"
        }
    }
}