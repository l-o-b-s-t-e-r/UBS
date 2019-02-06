package com.lobster.usb.domain.pojo

import com.lobster.usb.presentation.view.adapter.AdapterTypes
import com.lobster.usb.presentation.view.adapter.RecyclerViewItem

data class Symbol(
    val id: Long,

    val symbolCode: String,

    val companyName: String,

    val change: Double,

    val lastPrice: Double,

    var isFavorite: Boolean = false
) : RecyclerViewItem {
    override var defaultViewType = AdapterTypes.SYMBOL
}