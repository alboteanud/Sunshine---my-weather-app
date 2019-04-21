package com.craiovadata.android.sunshine.ui.settings

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.preference.*
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class MySettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
//        bindPreferenceSummaryToValue(findPreference(getString(R.string.key_pref_feedback)))

        val feedbackPref = findPreference(getString(R.string.key_pref_feedback))
        feedbackPref.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, value ->
            val stringValue = value.toString()
            // send feedback to Firebase
            sendFeedbackToFirebase(stringValue)
            true

        }

    }

    private fun sendFeedbackToFirebase(stringValue: String) {
        if (stringValue.isEmpty()) return

        val review = HashMap<String, Any>()
        review["review"] = stringValue
        review["timestamp"] = FieldValue.serverTimestamp()

        val db = FirebaseFirestore.getInstance()

// Add a new document with a generated ID
        db.collection("SunshineApps").document(BuildConfig.APPLICATION_ID).collection("reviews")
                .add(review)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(activity, getString(R.string.feedback_review_sent), Toast.LENGTH_LONG).show()
                    clearFeedbackPref()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(activity, getString(R.string.feedback_review_sent_unsuccesfully), Toast.LENGTH_SHORT).show()
                }
    }

    private fun clearFeedbackPref() {
        val feedbackPref = findPreference(getString(R.string.key_pref_feedback)) as EditTextPreference

        getDefaultSharedPreferences(feedbackPref.context).edit().remove(feedbackPref.key).apply()
        feedbackPref.summary = getString(R.string.sent_feedback_label)
        feedbackPref.text = null

    }

    private fun bindPreferenceSummaryToValue(preference: androidx.preference.Preference) {
        // Set the listener to watch for value changes.
        preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

        // Trigger the listener immediately with the preference's
        // current value.

        sBindPreferenceSummaryToValueListener.onPreferenceChange(
                preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.context)
                        .getString(preference.key, "")
        )
    }

    companion object {

        val TAG = "MySettingsFragment"

        private val sBindPreferenceSummaryToValueListener = androidx.preference.Preference.OnPreferenceChangeListener { preference, value ->
            val stringValue = value.toString()

            if (preference is ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                val listPreference = preference
                val index = listPreference.findIndexOfValue(stringValue)

                // Set the summary to reflect the new value.
                preference.setSummary(
                        if (index >= 0)
                            listPreference.entries[index]
                        else
                            null
                )

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.summary = stringValue
            }
            true
        }

    }

}