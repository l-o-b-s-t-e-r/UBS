package com.lobster.usb.presentation.view.adapter

/**
 * Created by Lobster on 25.11.17.
 */
interface RecyclerViewItem {
    var defaultViewType: Int

    fun alternativeViewTypes() = listOf<Int>()
}