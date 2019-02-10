package com.lobster.usb.domain.pojo

import com.lobster.usb.presentation.view.adapter.AdapterTypes
import com.lobster.usb.presentation.view.adapter.RecyclerViewItem

data class Symbol(
    val id: Long,

    val symbolCode: String = "",

    val companyName: String = "",

    val change: Double = 0.0,

    val lastPrice: Double = 0.0,

    var isFavorite: Boolean = false
) : RecyclerViewItem {
    override var defaultViewType = AdapterTypes.SYMBOL

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Symbol

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}