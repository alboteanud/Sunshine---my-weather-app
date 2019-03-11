package com.example.android.sunshine.ui.test

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.sunshine.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val txt = getSharedPreferences("_", Context.MODE_PRIVATE).getString("txt", "")
        text.text = txt
    }
}
