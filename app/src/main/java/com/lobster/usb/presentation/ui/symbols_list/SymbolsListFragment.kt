package com.lobster.usb.presentation.ui.symbols_list


import android.content.Context
import android.os.Bundle
import android.view.View
import com.lobster.usb.App
import com.lobster.usb.R
import com.lobster.usb.domain.dto.SymbolDto
import com.lobster.usb.presentation.presenters.ISymbolsListPresenter
import com.lobster.usb.presentation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_symbols_list.*

class SymbolsListFragment : BaseFragment<ISymbolsListPresenter.View, ISymbolsListPresenter.Actions>(),
    ISymbolsListPresenter.View {

    lateinit var symbolsAdapter: SymbolsListAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        symbolsAdapter = SymbolsListAdapter {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listSymbols.adapter = symbolsAdapter
        presenter.getSymbols()
    }

    override fun showSymbols(symbols: List<SymbolDto>) {
        symbolsAdapter.updateItems(symbols)
    }

    override fun inject() {
        App.instance.initSymbolsListComponent()?.inject(this)
    }

    override fun layoutId() = R.layout.fragment_symbols_list

}
