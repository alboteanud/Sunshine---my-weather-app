package com.craiovadata.android.sunshine.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.craiovadata.android.sunshine.data.database.Repository

class
ViewModelFactory(private val mRepository: Repository) :
    NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyViewModel(mRepository) as T
    }

}