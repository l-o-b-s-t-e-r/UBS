package com.lobster.usb.presentation.view.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.lobster.usb.presentation.view.adapter.AdapterTypes.DEFAULT
import com.lobster.usb.utils.inflate
import org.jetbrains.anko.collections.forEachReversedWithIndex
import java.util.*

/**
 * Created by Lobster on 25.11.17.
 */
abstract class BaseRecyclerViewAdapter<T : Any>() :
    RecyclerView.Adapter<ViewTypeDelegateAdapter<out T>.BaseViewHolder>() {

    open val layoutId: Int = 0

    protected val items = mutableListOf<T>()
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter<out T>>()
    private val defaultDelegateAdapter = DefaultDelegateAdapter()

    init {
        delegateAdapters.put(DEFAULT, defaultDelegateAdapter)
    }

    constructor(vararg adapters: Pair<Int, ViewTypeDelegateAdapter<out T>>) : this() {
        addAdapters(*adapters)
    }

    open fun onBindDefaultView(item: T, view: View, position: Int) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewTypeDelegateAdapter<out T>.BaseViewHolder {
        return getDelegateAdapter(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewTypeDelegateAdapter<out T>.BaseViewHolder, position: Int) {
        getDelegateAdapter(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is RecyclerViewItem) {
            val item = (items[position] as RecyclerViewItem)
            if (delegateAdapters[item.defaultViewType] == null && item.alternativeViewTypes().isNotEmpty()) {
                item.alternativeViewTypes().find { delegateAdapters[it] != null } ?: DEFAULT
            } else {
                item.defaultViewType
            }
        } else {
            DEFAULT
        }
    }

    private fun getDelegateAdapter(viewType: Int): ViewTypeDelegateAdapter<out T> {
        return delegateAdapters[viewType] ?: defaultDelegateAdapter
    }

    open fun addAdapter(type: Int, adapter: ViewTypeDelegateAdapter<out T>) {
        delegateAdapters.put(type, adapter)
    }

    fun addAdapters(vararg adapters: Pair<Int, ViewTypeDelegateAdapter<out T>>) {
        adapters.forEach { delegateAdapters.put(it.first, it.second) }
    }

    open fun isEmpty(): Boolean {
        return items.size == 0
    }

    open fun isNotEmpty(): Boolean {
        return items.size != 0
    }

    open fun add(item: T) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }

    open fun addAll(newItems: List<T>?, position: Int = items.size) {
        if (newItems == null) {
            clear()
        } else {
            items.addAll(position, newItems)
            notifyItemRangeInserted(position, newItems.size)
        }
    }

    open fun addItem(position: Int, item: T) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    open fun updateItems(newItems: List<T>, startPosition: Int = 0, endPosition: Int = items.size) {
        val newMutableList = newItems.toMutableList()
        newMutableList.addAll(0, items.subList(0, startPosition))

        if (this.items.isNotEmpty()) {
            newMutableList.addAll(this.items.subList(endPosition, this.items.size))
        }

        this.items.clear()
        this.items.addAll(newMutableList)

        notifyDataSetChanged()
    }

    open fun updateItem(item: T) {
        val position = getPosition(item)
        items[position] = item
        notifyItemChanged(position)
    }

    open fun updateItem(item: T, position: Int) {
        items[position] = item
        notifyItemChanged(position)
    }

    open fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    open fun getItem(position: Int): T? {
        try {
            return items[position]
        } catch (e: IndexOutOfBoundsException) {
            return null
        }
    }

    override fun getItemCount() = items.size

    open fun getPosition(item: T): Int {
        return items.indexOf(item)
    }

    open fun remove(item: T) {
        val position = getPosition(item)
        items.remove(item)
        notifyItemRemoved(position)
    }

    open fun getLastItem() = if (items.isEmpty()) null else items.last()

    open fun removeLastItem() {
        remove(items.lastIndex)
    }

    open fun replace(oldItem: T, newItem: T) {
        val position = getPosition(oldItem)
        items.removeAt(position)
        items.add(position, newItem)
        notifyItemChanged(position)
    }

    open fun replaceWithAnimations(items: List<T>) {
        animateRemoveItems(items)
        animateAddItems(items)
    }

    open fun sort(comparator: Comparator<in T>) {
        Collections.sort(items, comparator)
        notifyItemRangeChanged(0, items.size)
    }

    open fun remove(position: Int): T {
        val item = items.removeAt(position)
        notifyItemRemoved(position)
        return item
    }

    private fun animateAddItems(newItems: List<T>) {
        newItems.filter { !items.contains(it) }
            .forEach { add(it) }
    }

    private fun animateRemoveItems(newItems: List<T>) {
        items.filter { !items.contains(it) }
            .forEachReversedWithIndex { i, t -> remove(i) }
    }

    private fun moveItem(fromPosition: Int, toPosition: Int) {
        val item = items.removeAt(fromPosition)
        items.add(toPosition, item)
        notifyItemMoved(fromPosition, toPosition)
    }

    open inner class DefaultDelegateAdapter : ViewTypeDelegateAdapter<T>() {
        override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder {
            return DefaultViewHolder(parent.inflate(layoutId))
        }

        open inner class DefaultViewHolder(view: View) : BaseViewHolder(view) {
            override fun bindView(item: T) {
                onBindDefaultView(item, view, adapterPosition)
            }
        }
    }
}