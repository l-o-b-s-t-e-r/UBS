package com.lobster.usb.presentation.ui.symbols_list


import android.content.Context
import android.os.Bundle
import android.support.transition.Fade
import android.support.transition.TransitionInflater
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
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
import com.lobster.usb.presentation.ui.symbol_details.SymbolDetailsFragment
import com.lobster.usb.presentation.ui.symbol_details.SymbolDetailsFragment.Companion.SYMBOL_ID
import com.lobster.usb.presentation.view.adapter.AdapterTypes.SPINNER
import com.lobster.usb.presentation.view.adapter.AdapterTypes.SYMBOL
import com.lobster.usb.presentation.view.adapter.EndlessRecyclerViewScrollListener
import com.lobster.usb.presentation.view.adapter.SimpleItemTouchHelperCallback
import com.lobster.usb.utils.replace
import kotlinx.android.synthetic.main.fragment_symbols_list.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.withArguments


class SymbolsListFragment : BaseFragment<ISymbolsListPresenter.View, ISymbolsListPresenter.Actions>(),
    ISymbolsListPresenter.View {

    lateinit var symbolsAdapter: SymbolsListAdapter
    lateinit var symbolSuggestionsAdapter: ArrayAdapter<String>
    lateinit var symbolsRecyclerScrollListener: EndlessRecyclerViewScrollListener
    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        symbolsAdapter = SymbolsListAdapter({ viewHolder ->
            itemTouchHelper.startDrag(viewHolder)
        }, { symbol, views ->
            presenter.addSymbolChangeListener(symbol)
            replaceFragment(symbol, views)
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
        if (isRestoredFromBackStack) {
            symbolsRecyclerScrollListener.mLayoutManager = listSymbols.layoutManager!!
            setupRecyclerView()
            setupSymbolSuggestions()
            presenter.updateSelectedSymbol()
        } else {
            symbolsRecyclerScrollListener = createScrollListener(listSymbols.layoutManager!!)
            setupRecyclerView()
            presenter.getSymbolCodes()
        }

        setupSwipeToRefresh()

        btnSearch.onClick { performNewSearch() }
    }

    override fun showSymbols(symbols: List<Symbol>) {
        symbolsAdapter.addAll(symbols, symbolsAdapter.itemCount - 1)
    }

    override fun enableSuggestions(symbolCodes: List<String>) {
        symbolSuggestionsAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_dropdown_item_1line,
            symbolCodes
        )
        setupSymbolSuggestions()
    }

    override fun disableScrollListener() {
        listSymbols.clearOnScrollListeners()
    }

    override fun updateSymbol(symbol: Symbol) {
        symbolsAdapter.updateItem(symbol)
    }

    override fun showLoading() {
        if (symbolsAdapter.isEmpty()) {
            spinnerMain.visibility = VISIBLE
        } else if (symbolsAdapter.getLastItem() != Spinner) {
            symbolsAdapter.add(Spinner)
        }
    }

    override fun hideLoading() {
        if (spinnerMain.visibility == VISIBLE) {
            spinnerMain.visibility = GONE
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

    private fun replaceFragment(symbol: Symbol, views: List<Triple<View, String, String>>) {
        val params = mutableListOf<Pair<String, Any>>(SYMBOL_ID to symbol.id)
        val sharedViews = mutableMapOf<View, String>()
        views.forEach {
            params.add(it.second to it.third)
            sharedViews.put(it.first, it.third)
        }

        val fragment = SymbolDetailsFragment().withArguments(*params.toTypedArray())
        val fragmentTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        this.sharedElementReturnTransition = fragmentTransition
        this.exitTransition = Fade()

        fragment.sharedElementEnterTransition = fragmentTransition
        fragment.enterTransition = Fade()


        fragmentManager?.replace(
            R.id.rootContainer, fragment, true, sharedViews
        )
    }

    private fun setupRecyclerView() {
        listSymbols.adapter = symbolsAdapter
        listSymbols.addOnScrollListener(symbolsRecyclerScrollListener)
        listSymbols.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            setDrawable(ContextCompat.getDrawable(context!!, R.drawable.divider_symbols)!!)
        })

        itemTouchHelper.attachToRecyclerView(listSymbols)
    }

    private fun setupSymbolSuggestions() {
        editTxtSearch.setAdapter(symbolSuggestionsAdapter)
        editTxtSearch.setOnItemClickListener { _, _, _, _ -> performNewSearch() }
    }

    private fun createScrollListener(layoutManager: RecyclerView.LayoutManager): EndlessRecyclerViewScrollListener {
        return object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                presenter.getSymbols(editTxtSearch.text.trim().toString(), page)
            }
        }
    }

    private fun setupSwipeToRefresh() {
        swipeToRefresh.setOnRefreshListener {
            performNewSearch()
            swipeToRefresh.isRefreshing = false
        }
    }
}
