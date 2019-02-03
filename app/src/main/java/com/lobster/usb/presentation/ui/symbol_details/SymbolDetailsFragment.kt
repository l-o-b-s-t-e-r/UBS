package com.lobster.usb.presentation.ui.symbol_details


import com.lobster.usb.R
import com.lobster.usb.presentation.presenters.ISymbolDetailsPresenter
import com.lobster.usb.presentation.ui.base.BaseFragment

class SymbolDetailsFragment : BaseFragment<ISymbolDetailsPresenter.View, ISymbolDetailsPresenter.Actions>(), ISymbolDetailsPresenter.View {

    override fun inject() {

    }

    override fun layoutId() = R.layout.fragment_symbol_details

}
