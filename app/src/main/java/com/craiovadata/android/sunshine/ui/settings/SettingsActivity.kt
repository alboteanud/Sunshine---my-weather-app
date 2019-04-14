package com.craiovadata.android.sunshine.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.craiovadata.android.sunshine.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(settings_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val fragment = MySettingsFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.settings_container, fragment)
                    .commit()
        }


    }
}