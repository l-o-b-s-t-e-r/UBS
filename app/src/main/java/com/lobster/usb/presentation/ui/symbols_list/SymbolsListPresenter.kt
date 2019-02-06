package com.lobster.usb.presentation.ui.symbols_list

import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.domain.usecases.GetSymbolsUseCase
import com.lobster.usb.domain.usecases.base.CustomSingleObserver
import com.lobster.usb.presentation.presenters.ISymbolsListPresenter
import com.lobster.usb.utils.RxUtils
import javax.inject.Inject

class SymbolsListPresenter @Inject constructor(private val getSymbolsUseCase: GetSymbolsUseCase) :
    ISymbolsListPresenter.Actions() {

    override fun getSymbols(query: String, page: Int) {
        getSymbolsUseCase.execute(object : CustomSingleObserver<List<Symbol>>() {
            override fun onSuccess(t: List<Symbol>) {
                view?.showSymbols(t)
            }
        },  RxUtils.applyLoadingSchedulersSng(view), GetSymbolsUseCase.Params(query, page))
    }
}