package com.lobster.usb

import android.support.multidex.MultiDexApplication
import com.lobster.usb.presentation.di.*

class App : MultiDexApplication() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent
    private var symbolsListComponent: SymbolsListComponent? = null
    private var symbolDetailsComponent: SymbolDetailsComponent? = null

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

    fun initSymbolDetailsComponent(): SymbolDetailsComponent? {
        if (symbolDetailsComponent == null) {
            symbolDetailsComponent = appComponent.symbolDetailsComponent(SymbolDetailsModule())
        }

        return symbolDetailsComponent
    }

    fun destroyListComponent() {
        symbolsListComponent = null
    }

    fun destroyDetailsComponent() {
        symbolDetailsComponent = null
    }
}