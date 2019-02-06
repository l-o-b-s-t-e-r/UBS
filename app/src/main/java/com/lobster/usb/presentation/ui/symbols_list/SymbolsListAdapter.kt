package com.lobster.usb.presentation.ui.symbols_list

import android.view.View
import android.view.ViewGroup
import com.lobster.usb.R
import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.presentation.view.adapter.BaseRecyclerViewAdapter
import com.lobster.usb.presentation.view.adapter.RecyclerViewItem
import com.lobster.usb.presentation.view.adapter.ViewTypeDelegateAdapter
import com.lobster.usb.utils.inflate
import kotlinx.android.synthetic.main.item_symbol.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class SymbolsListAdapter(val onItemClick: (Symbol) -> Unit) : BaseRecyclerViewAdapter<RecyclerViewItem>() {

    inner class SymbolsAdapter : ViewTypeDelegateAdapter<Symbol>() {
        override fun onCreateViewHolder(parent: ViewGroup) = SymbolsViewHolder(parent.inflate(R.layout.item_symbol))

        inner class SymbolsViewHolder(view: View) : BaseViewHolder(view) {
            override fun bindView(symbol: Symbol) {
                with(view) {
                    txtSymbolCode.text = symbol.symbolCode
                    txtCompanyName.text = symbol.companyName
                    txtChange.text = symbol.change.toString()
                    txtLastPrice.text = symbol.lastPrice.toString()
                    onClick { onItemClick.invoke(symbol) }
                }
            }
        }
    }

    inner class SpinnerAdapter : ViewTypeDelegateAdapter<RecyclerViewItem>() {
        override fun onCreateViewHolder(parent: ViewGroup) = SpinnerViewHolder(parent.inflate(R.layout.item_spinner))

        inner class SpinnerViewHolder(view: View) : BaseViewHolder(view) {
            override fun bindView(item: RecyclerViewItem) {

            }
        }
    }

}