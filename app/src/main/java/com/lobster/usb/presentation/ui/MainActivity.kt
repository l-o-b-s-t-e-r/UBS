package com.lobster.usb.presentation.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lobster.usb.App
import com.lobster.usb.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)
    }
}
