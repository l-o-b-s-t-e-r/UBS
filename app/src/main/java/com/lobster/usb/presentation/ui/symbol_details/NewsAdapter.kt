package com.lobster.usb.presentation.ui.symbol_details

import android.view.View
import com.lobster.usb.R
import com.lobster.usb.domain.pojo.SymbolNews
import com.lobster.usb.presentation.view.adapter.BaseRecyclerViewAdapter
import com.lobster.usb.utils.GlideApp
import kotlinx.android.synthetic.main.item_news.view.*
import java.text.SimpleDateFormat


class NewsAdapter : BaseRecyclerViewAdapter<SymbolNews>() {

    private val sdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm")

    override val layoutId = R.layout.item_news

    override fun onBindDefaultView(news: SymbolNews, view: View, position: Int) {
        with(view) {
            txtDate.text = "${sdf.format(news.date)} | ${news.source}"
            txtTitle.text = news.title
            txtDescription.text = news.description.trim()
            GlideApp.with(this)
                .load(news.image)
                .placeholder(R.drawable.placeholder)
                .into(imgCover)

            val tagsAdapter = TagsAdapter()
            listRelated.adapter = tagsAdapter
            tagsAdapter.addAll(news.symbolCodes)
        }
    }
}