package com.craiovadata.android.sunshine.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.craiovadata.android.sunshine.R

class MySettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val button = findPreference(getString(R.string.button_feedback)) as? Preference
        button?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            composeEmail()
            true
        }
    }

    private fun composeEmail() {
        val appName = getString(R.string.app_name)
        val appFeedback = getString(R.string.feedback_pref_title)
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf("craiova.data@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "$appName - app feedback")
            putExtra(Intent.EXTRA_TEXT, "$appFeedback: ")
        }
        val pm = activity?.packageManager
        if (pm?.let { intent.resolveActivity(it) } != null) {
            startActivity(intent)
        }
    }


}