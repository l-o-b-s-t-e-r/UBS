package com.lobster.usb.presentation.di

import android.app.Application
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideApplications() = app

}
