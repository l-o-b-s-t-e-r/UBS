package com.lobster.usb.presentation.ui.symbols_list

import android.view.View
import com.lobster.usb.R
import com.lobster.usb.domain.dto.SymbolDto
import com.lobster.usb.presentation.view.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_symbol.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class SymbolsListAdapter(val onItemClick: (SymbolDto) -> Unit) : BaseRecyclerViewAdapter<SymbolDto>() {

    override val layoutId = R.layout.item_symbol

    override fun onBindDefaultView(symbol: SymbolDto, view: View, position: Int) {
        with(view) {
            txtSymbolCode.text = symbol.symbolCode
            txtCompanyName.text = symbol.companyName
            txtChange.text = symbol.change.toString()
            txtLastPrice.text = symbol.lastPrice.toString()

            onClick { onItemClick.invoke(symbol) }
        }
    }

}