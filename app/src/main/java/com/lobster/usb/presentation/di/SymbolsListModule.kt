package com.lobster.usb.presentation.di

import com.lobster.usb.domain.usecases.AddToFavoriteUseCase
import com.lobster.usb.domain.usecases.GetSymbolsUseCase
import com.lobster.usb.presentation.presenters.ISymbolsListPresenter
import com.lobster.usb.presentation.ui.symbols_list.SymbolsListPresenter
import dagger.Module
import dagger.Provides

@Module
class SymbolsListModule {

    @Provides
    @FragmentScope
    fun providePresenter(getSymbolsUseCase: GetSymbolsUseCase, addToFavoriteUseCase: AddToFavoriteUseCase): ISymbolsListPresenter.Actions =
        SymbolsListPresenter(getSymbolsUseCase, addToFavoriteUseCase)

}