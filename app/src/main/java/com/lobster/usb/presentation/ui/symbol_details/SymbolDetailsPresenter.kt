package com.lobster.usb.presentation.ui.symbol_details

import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.domain.pojo.SymbolCompany
import com.lobster.usb.domain.usecases.AddToFavoriteUseCase
import com.lobster.usb.domain.usecases.GetSymbolCompanyUseCase
import com.lobster.usb.domain.usecases.base.CustomSingleObserver
import com.lobster.usb.presentation.presenters.ISymbolDetailsPresenter
import com.lobster.usb.utils.RxUtils

class SymbolDetailsPresenter(
    private val getSymbolCompanyUseCase: GetSymbolCompanyUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase
) : ISymbolDetailsPresenter.Actions() {

    override fun getSymbolCompany(symbol: String) {
        getSymbolCompanyUseCase.execute(object : CustomSingleObserver<SymbolCompany>() {
            override fun onSuccess(t: SymbolCompany) {
                view?.showSymbolCompany(t)
            }
        }, RxUtils.applyLoadingSchedulersSng(view), GetSymbolCompanyUseCase.Params(symbol))
    }

    override fun addToFavorite(symbol: Symbol, isFavorite: Boolean) {
        addToFavoriteUseCase.execute(AddToFavoriteUseCase.Params(symbol, isFavorite))
    }
}