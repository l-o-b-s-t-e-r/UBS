package com.lobster.usb.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by Lobster on 25.11.17.
 */
abstract class ViewTypeDelegateAdapter<T> {

    abstract fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder

    fun onBindViewHolder(holder: Any, item: Any) {
        (holder as ViewTypeDelegateAdapter<T>.BaseViewHolder).bindView(item as T)
    }

    abstract inner class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindView(item: T)
    }
}