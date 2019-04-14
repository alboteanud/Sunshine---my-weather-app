package com.craiovadata.android.sunshine.ui.policy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_policy.*
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.utilities.Utils.addLinks


class PolicyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy)

        setSupportActionBar(policy_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val link1 = getString(R.string.link_privacy_policy)
        val word1 = getString(R.string.word_to_linkify_privacy_policy)

          val link2 = getString(R.string.link_terms_of_use)
        val word2 = getString(R.string.word_to_linkify_terms_of_use)


        addLinks(textPrivacyPolicy, word1, link1)
        addLinks(textPrivacyPolicy, word2, link2)
    }







}
