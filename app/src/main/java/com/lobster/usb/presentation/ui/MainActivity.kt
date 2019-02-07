package com.lobster.usb.presentation.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lobster.usb.R
import com.lobster.usb.presentation.ui.symbols_list.SymbolsListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.rootContainer, SymbolsListFragment())
            .commit()
    }
}
