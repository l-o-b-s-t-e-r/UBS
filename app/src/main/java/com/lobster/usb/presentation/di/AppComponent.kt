package com.lobster.usb.presentation.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {

    fun symbolsListComponent(module: SymbolsListModule): SymbolsListComponent

    fun symbolDetailsComponent(module: SymbolDetailsModule): SymbolDetailsComponent

}
