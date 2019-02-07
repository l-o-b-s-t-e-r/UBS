package com.lobster.usb.presentation.ui.symbols_list

import android.graphics.Color
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.lobster.usb.R
import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.presentation.ui.symbol_details.SymbolDetailsFragment.Companion.CHANGE
import com.lobster.usb.presentation.ui.symbol_details.SymbolDetailsFragment.Companion.COMPANY
import com.lobster.usb.presentation.ui.symbol_details.SymbolDetailsFragment.Companion.IS_FAVORITE
import com.lobster.usb.presentation.ui.symbol_details.SymbolDetailsFragment.Companion.SYMBOL
import com.lobster.usb.presentation.view.adapter.*
import com.lobster.usb.utils.inflate
import kotlinx.android.synthetic.main.item_symbol.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import java.util.*


class SymbolsListAdapter(
    val onDrag: (RecyclerView.ViewHolder) -> Unit,
    val onItemClick: (Symbol, sharedElement: List<Triple<View, String, String>>) -> Unit,
    val onFavoriteClick: (Symbol, isFavorite: Boolean) -> Unit
) : BaseRecyclerViewAdapter<RecyclerViewItem>() {

    inner class SymbolsAdapter : ViewTypeDelegateAdapter<Symbol>(), ItemTouchHelperAdapter {
        override fun onCreateViewHolder(parent: ViewGroup) = SymbolsViewHolder(parent.inflate(R.layout.item_symbol))

        override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
            Collections.swap(items, fromPosition, toPosition)
            notifyItemMoved(fromPosition, toPosition)
            return true
        }

        override fun onItemDismiss(position: Int) {
            notifyItemChanged(position)
        }

        inner class SymbolsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
            override fun bindView(symbol: Symbol) {
                with(view) {
                    txtSymbolCode.text = symbol.symbolCode
                    txtCompanyName.text = symbol.companyName
                    txtChange.text = symbol.change.toString()
                    txtLastPrice.text = symbol.lastPrice.toString()
                    btnAddToFavorite.isChecked = symbol.isFavorite

                    btnAddToFavorite.onClick { view ->
                        symbol.isFavorite = !symbol.isFavorite
                        btnAddToFavorite.isChecked = symbol.isFavorite
                        onFavoriteClick.invoke(symbol, symbol.isFavorite)
                        ViewCompat.setTransitionName(txtChange, symbol.isFavorite.toString())
                    }
                    onClick {
                        onItemClick.invoke(
                            symbol,
                            listOf(
                                Triple(txtSymbolCode, SYMBOL, ViewCompat.getTransitionName(txtSymbolCode)!!),
                                Triple(txtCompanyName, COMPANY, ViewCompat.getTransitionName(txtCompanyName)!!),
                                Triple(txtChange, CHANGE, ViewCompat.getTransitionName(txtChange)!!),
                                Triple(btnAddToFavorite, IS_FAVORITE, ViewCompat.getTransitionName(btnAddToFavorite)!!)
                            )
                        )
                    }
                    onLongClick { onDrag.invoke(this@SymbolsViewHolder) }

                    ViewCompat.setTransitionName(txtSymbolCode, symbol.symbolCode)
                    ViewCompat.setTransitionName(txtCompanyName, symbol.companyName)
                    ViewCompat.setTransitionName(txtChange, symbol.change.toString())
                    ViewCompat.setTransitionName(btnAddToFavorite, "${symbol.symbolCode} ${symbol.isFavorite}")
                }
            }

            override fun onItemSelected() {
                itemView.setBackgroundColor(Color.LTGRAY)
            }

            override fun onItemClear() {
                itemView.setBackgroundColor(0)
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