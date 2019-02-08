package com.lobster.usb.presentation.ui.symbol_details


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.view.View
import com.lobster.usb.App
import com.lobster.usb.R
import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.domain.pojo.SymbolCompany
import com.lobster.usb.presentation.presenters.ISymbolDetailsPresenter
import com.lobster.usb.presentation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_symbol_details.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.textColor


class SymbolDetailsFragment : BaseFragment<ISymbolDetailsPresenter.View, ISymbolDetailsPresenter.Actions>(),
    ISymbolDetailsPresenter.View {

    companion object {
        const val SYMBOL_ID = "symbol_id"
        const val SYMBOL = "symbol"
        const val COMPANY = "company"
        const val CHANGE = "change"
        const val IS_FAVORITE = "is_favorite"
    }

    lateinit var newsAdapter: NewsAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        newsAdapter = NewsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtSymbolCode.transitionName = arguments!!.getString(SYMBOL)
        txtCompanyName.transitionName = arguments!!.getString(COMPANY)
        txtChange.transitionName = arguments!!.getString(CHANGE)
        btnAddToFavorite.transitionName = arguments!!.getString(IS_FAVORITE)

        txtSymbolCode.text = arguments!!.getString(SYMBOL)
        txtCompanyName.text = arguments!!.getString(COMPANY)
        btnAddToFavorite.isChecked = arguments!!.getString(IS_FAVORITE)!!.substringAfter(" ").toBoolean()
        val change = arguments!!.getString(CHANGE)!!.toDouble()
        txtChange.text = "${change}%"
        txtChange.textColor = if (change < 0) ContextCompat.getColor(context!!, R.color.colorAccent) else Color.GREEN

        listNews.adapter = newsAdapter
        listNews.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            setDrawable(ContextCompat.getDrawable(context!!, R.drawable.divider_news)!!)
        })

        btnAddToFavorite.onClick {
            presenter.addToFavorite(Symbol(arguments!!.getLong(SYMBOL_ID)), btnAddToFavorite.isChecked)
        }

        presenter.getSymbolCompany(arguments!!.getString(SYMBOL)!!)
    }

    override fun showLoading() {
        spinner.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        spinner.visibility = View.GONE
    }

    override fun showSymbolCompany(symbolCompany: SymbolCompany) {
        txtCeoName.text = symbolCompany.ceo
        txtDescription.text = symbolCompany.description
        txtWebsite.text = symbolCompany.website

        newsAdapter.updateItems(symbolCompany.news)
    }

    override fun inject() {
        App.instance.initSymbolDetailsComponent()?.inject(this)
    }

    override fun layoutId() = R.layout.fragment_symbol_details

}
