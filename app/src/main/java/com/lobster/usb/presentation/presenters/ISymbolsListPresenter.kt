package com.lobster.usb.presentation.presenters

import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.presentation.presenters.base.IBasePresenter

interface ISymbolsListPresenter {

    interface View : IBasePresenter.View {
        fun showSymbols(symbols: List<Symbol>)

        fun enableSuggestions(symbolCodes: List<String>)

        fun disableScrollListener()
    }

    abstract class Actions : IBasePresenter.Actions<ISymbolsListPresenter.View>() {
        abstract fun getSymbols(query: String = "", page: Int = 0)

        abstract fun addToFavorite(symbol: Symbol, isFavorite: Boolean)

        abstract fun getSymbolCodes()
    }

}