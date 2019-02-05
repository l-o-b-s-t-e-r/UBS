package com.lobster.usb.presentation.presenters

import com.lobster.usb.domain.dto.SymbolDto
import com.lobster.usb.presentation.presenters.base.IBasePresenter

interface ISymbolsListPresenter {

    interface View : IBasePresenter.View {
        fun showSymbols(symbols: List<SymbolDto>)
    }

    abstract class Actions : IBasePresenter.Actions<ISymbolsListPresenter.View>() {
        abstract fun getSymbols(query: String = "")
    }

}