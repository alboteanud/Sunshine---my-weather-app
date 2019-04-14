package com.craiovadata.android.sunshine.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.craiovadata.android.sunshine.R

class MySettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefs, rootKey)
    }
}