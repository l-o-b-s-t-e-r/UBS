package com.lobster.usb

import android.support.multidex.MultiDexApplication
import com.lobster.usb.presentation.di.*

class App : MultiDexApplication() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent
    private var symbolsListComponent: SymbolsListComponent? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    fun initSymbolsListComponent(): SymbolsListComponent? {
        if (symbolsListComponent == null) {
            symbolsListComponent = appComponent.symbolsListComponent(SymbolsListModule())
        }

        return symbolsListComponent
    }

    fun destroyListComponent() {
        symbolsListComponent = null
    }
}