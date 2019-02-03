package com.lobster.usb.presentation.ui.symbols_list


import com.lobster.usb.R
import com.lobster.usb.presentation.presenters.ISymbolsListPresenter
import com.lobster.usb.presentation.ui.base.BaseFragment

class SymbolsListFragment : BaseFragment<ISymbolsListPresenter.View, ISymbolsListPresenter.Actions>(), ISymbolsListPresenter.View {

    override fun inject() {

    }

    override fun layoutId() = R.layout.fragment_symbols_list

}
