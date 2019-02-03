package com.lobster.usb

import android.support.multidex.MultiDexApplication
import com.lobster.usb.presentation.di.AppComponent
import com.lobster.usb.presentation.di.AppModule
import com.lobster.usb.presentation.di.DaggerAppComponent

class App : MultiDexApplication() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}