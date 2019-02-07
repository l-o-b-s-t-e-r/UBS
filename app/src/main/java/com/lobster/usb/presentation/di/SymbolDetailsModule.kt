package com.lobster.usb.presentation.di

import com.lobster.usb.domain.usecases.AddToFavoriteUseCase
import com.lobster.usb.domain.usecases.GetSymbolCompanyUseCase
import com.lobster.usb.presentation.presenters.ISymbolDetailsPresenter
import com.lobster.usb.presentation.ui.symbol_details.SymbolDetailsPresenter
import dagger.Module
import dagger.Provides

@Module
class SymbolDetailsModule {

    @Provides
    @FragmentScope
    fun providePresenter(getSymbolCompanyUseCase: GetSymbolCompanyUseCase,
                         addToFavoriteUseCase: AddToFavoriteUseCase): ISymbolDetailsPresenter.Actions =
        SymbolDetailsPresenter(getSymbolCompanyUseCase, addToFavoriteUseCase)

}