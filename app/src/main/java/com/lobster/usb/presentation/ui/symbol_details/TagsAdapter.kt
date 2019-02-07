package com.lobster.usb.presentation.ui.symbol_details

import android.view.View
import com.lobster.usb.R
import com.lobster.usb.presentation.view.adapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_tag.view.*

class TagsAdapter : BaseRecyclerViewAdapter<String>() {

    override val layoutId = R.layout.item_tag

    override fun onBindDefaultView(tag: String, view: View, position: Int) {
        with(view) {
            txtTag.text = tag
        }
    }
}