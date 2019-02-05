package com.lobster.usb.presentation.di

import com.lobster.usb.domain.usecases.GetSymbolsUseCase
import com.lobster.usb.presentation.presenters.ISymbolsListPresenter
import com.lobster.usb.presentation.ui.symbols_list.SymbolsListPresenter
import dagger.Module
import dagger.Provides

@Module
class SymbolsListModule {

    @Provides
    @FragmentScope
    fun providePresenter(getSymbolsUseCase: GetSymbolsUseCase): ISymbolsListPresenter.Actions =
        SymbolsListPresenter(getSymbolsUseCase)

}