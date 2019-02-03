package com.lobster.usb.presentation.di

import com.lobster.usb.presentation.ui.MainActivity
import dagger.Component

import javax.inject.Singleton

@Component(modules = [AppModule::class, DataModule::class])
@Singleton
interface AppComponent {

    fun inject(activity: MainActivity)

}
