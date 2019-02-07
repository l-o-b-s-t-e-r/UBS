package com.lobster.usb.presentation.presenters

import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.domain.pojo.SymbolCompany
import com.lobster.usb.presentation.presenters.base.IBasePresenter

interface ISymbolDetailsPresenter {

    interface View : IBasePresenter.View {
        fun showSymbolCompany(symbolCompany: SymbolCompany)
    }

    abstract class Actions : IBasePresenter.Actions<ISymbolDetailsPresenter.View>() {
        abstract fun getSymbolCompany(symbol: String)

        abstract fun addToFavorite(symbol: Symbol, isFavorite: Boolean)
    }

}