package com.lobster.usb.presentation.ui.symbols_list


import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import com.lobster.usb.App
import com.lobster.usb.R
import com.lobster.usb.domain.pojo.Spinner
import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.presentation.presenters.ISymbolsListPresenter
import com.lobster.usb.presentation.ui.base.BaseFragment
import com.lobster.usb.presentation.view.adapter.AdapterTypes.SPINNER
import com.lobster.usb.presentation.view.adapter.AdapterTypes.SYMBOL
import com.lobster.usb.presentation.view.adapter.EndlessRecyclerViewScrollListener
import com.lobster.usb.presentation.view.adapter.SimpleItemTouchHelperCallback
import kotlinx.android.synthetic.main.fragment_symbols_list.*
import org.jetbrains.anko.sdk27.coroutines.onClick


class SymbolsListFragment : BaseFragment<ISymbolsListPresenter.View, ISymbolsListPresenter.Actions>(),
    ISymbolsListPresenter.View {

    lateinit var symbolsAdapter: SymbolsListAdapter
    lateinit var symbolsRecyclerScrollListener: EndlessRecyclerViewScrollListener
    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        symbolsAdapter = SymbolsListAdapter({ viewHolder ->
            itemTouchHelper.startDrag(viewHolder)
        }, { symbol ->

        }, { symbol, isFavorite ->
            presenter.addToFavorite(symbol, isFavorite)
        })

        val innerSymbolsAdapter = symbolsAdapter.SymbolsAdapter()
        symbolsAdapter.addAdapter(SYMBOL, innerSymbolsAdapter)
        symbolsAdapter.addAdapter(SPINNER, symbolsAdapter.SpinnerAdapter())
        itemTouchHelper = ItemTouchHelper(SimpleItemTouchHelperCallback(innerSymbolsAdapter))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        symbolsRecyclerScrollListener = object : EndlessRecyclerViewScrollListener(listSymbols.layoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                presenter.getSymbols(editTxtSearch.text.trim().toString(), page)
            }
        }

        listSymbols.adapter = symbolsAdapter
        listSymbols.addOnScrollListener(symbolsRecyclerScrollListener)
        itemTouchHelper.attachToRecyclerView(listSymbols)

        btnSearch.onClick { performNewSearch() }

        presenter.getSymbolCodes()
    }

    override fun showSymbols(symbols: List<Symbol>) {
        symbolsAdapter.addAll(symbols, symbolsAdapter.itemCount - 1)
    }

    override fun enableSuggestions(symbolCodes: List<String>) {
        editTxtSearch.setAdapter(
            ArrayAdapter<String>(
                context,
                android.R.layout.simple_dropdown_item_1line,
                symbolCodes
            )
        )
        editTxtSearch.setOnItemClickListener { parent, view, position, id ->
            performNewSearch()
        }
    }

    override fun disableScrollListener() {
        listSymbols.clearOnScrollListeners()
    }

    override fun showLoading() {
        if (symbolsAdapter.isEmpty()) {
            spinner.visibility = VISIBLE
        } else if (symbolsAdapter.getLastItem() != Spinner) {
            symbolsAdapter.add(Spinner)
        }
    }

    override fun hideLoading() {
        if (spinner.visibility == VISIBLE) {
            spinner.visibility = GONE
        } else if (symbolsAdapter.getLastItem() == Spinner) {
            symbolsAdapter.removeLastItem()
        }
    }

    override fun inject() {
        App.instance.initSymbolsListComponent()?.inject(this)
    }

    override fun layoutId() = R.layout.fragment_symbols_list

    private fun performNewSearch() {
        presenter.dispose()
        symbolsAdapter.clear()
        symbolsRecyclerScrollListener.resetState()
        listSymbols.addOnScrollListener(symbolsRecyclerScrollListener)
        presenter.getSymbols(editTxtSearch.text.trim().toString())
    }

}
