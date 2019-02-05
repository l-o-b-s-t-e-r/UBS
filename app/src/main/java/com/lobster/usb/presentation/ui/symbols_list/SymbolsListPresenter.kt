package com.lobster.usb.presentation.ui.symbols_list

import com.lobster.usb.domain.dto.SymbolDto
import com.lobster.usb.domain.usecases.GetSymbolsUseCase
import com.lobster.usb.domain.usecases.base.CustomSingleObserver
import com.lobster.usb.presentation.presenters.ISymbolsListPresenter
import javax.inject.Inject

class SymbolsListPresenter @Inject constructor(private val getSymbolsUseCase: GetSymbolsUseCase) :
    ISymbolsListPresenter.Actions() {

    override fun getSymbols(query: String) {
        getSymbolsUseCase.execute(object : CustomSingleObserver<List<SymbolDto>>() {
            override fun onSuccess(t: List<SymbolDto>) {
                view?.showSymbols(t)
            }
        }, GetSymbolsUseCase.Params(query))
    }
}