package com.lobster.usb.presentation.ui.symbols_list


import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lobster.usb.App
import com.lobster.usb.R
import com.lobster.usb.domain.pojo.Spinner
import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.presentation.presenters.ISymbolsListPresenter
import com.lobster.usb.presentation.ui.base.BaseFragment
import com.lobster.usb.presentation.view.adapter.AdapterTypes.SPINNER
import com.lobster.usb.presentation.view.adapter.AdapterTypes.SYMBOL
import com.lobster.usb.presentation.view.adapter.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.fragment_symbols_list.*

class SymbolsListFragment : BaseFragment<ISymbolsListPresenter.View, ISymbolsListPresenter.Actions>(),
    ISymbolsListPresenter.View {

    lateinit var symbolsAdapter: SymbolsListAdapter
    lateinit var symbolsRecyclerScrollListener: EndlessRecyclerViewScrollListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        symbolsAdapter = SymbolsListAdapter {

        }
        symbolsAdapter.addAdapter(SYMBOL, symbolsAdapter.SymbolsAdapter())
        symbolsAdapter.addAdapter(SPINNER, symbolsAdapter.SpinnerAdapter())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        symbolsRecyclerScrollListener = object : EndlessRecyclerViewScrollListener(listSymbols.layoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                presenter.getSymbols("", page)
            }
        }

        listSymbols.adapter = symbolsAdapter
        listSymbols.addOnScrollListener(symbolsRecyclerScrollListener)

        presenter.getSymbols("")
    }

    override fun showSymbols(symbols: List<Symbol>) {
        symbolsAdapter.removeLastItem()
        symbolsAdapter.addAll(symbols)
    }

    override fun showLoading() {
        if (symbolsAdapter.getLastItem() != Spinner) {
            symbolsAdapter.add(Spinner)
        }
    }

    override fun hideLoading() {
        if (symbolsAdapter.getLastItem() == Spinner) {
            symbolsAdapter.removeLastItem()
        }
    }

    override fun inject() {
        App.instance.initSymbolsListComponent()?.inject(this)
    }

    override fun layoutId() = R.layout.fragment_symbols_list

}
