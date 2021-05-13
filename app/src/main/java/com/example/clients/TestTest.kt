package com.example.clients

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.graphics.Color
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.activity_test_test.*


class TestTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_test)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        textViewTest.text = message
    }
}