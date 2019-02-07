package com.lobster.usb.utils

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup


/**
 * Created by Lobster on 01.04.18.
 */
fun FragmentManager.replace(@IdRes containerViewId: Int, fragment: Fragment, addToBackStack: Boolean = true, sharedElement: Map<View, String>? = null) {
    val transaction: FragmentTransaction
    if (addToBackStack) {
        transaction = beginTransaction()
                .replace(containerViewId, fragment, fragment::class.simpleName)
                .addToBackStack(fragment::class.simpleName)

    } else {
        transaction = beginTransaction()
                .replace(containerViewId, fragment)
    }

    sharedElement?.forEach {
        transaction.addSharedElement(it.key, it.value)
    }

    transaction.commitAllowingStateLoss()
}

fun FragmentManager.add(@IdRes containerViewId: Int, fragment: Fragment, addToBackStack: Boolean = true, sharedElement: Pair<View, String>? = null) {
    val transaction: FragmentTransaction
    if (addToBackStack) {
        transaction = beginTransaction()
                .add(containerViewId, fragment, fragment::class.simpleName)
                .addToBackStack(fragment::class.simpleName)

    } else {
        transaction = beginTransaction()
                .add(containerViewId, fragment)
    }

    if (sharedElement != null) {
        transaction.addSharedElement(sharedElement.first, sharedElement.second)
    }

    transaction.commitAllowingStateLoss()
}

fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}

fun View.setVisibleInvisible(isVisible: Boolean) {
    this.visibility = if (isVisible) VISIBLE else INVISIBLE
}

fun View.setVisibleGone(isVisible: Boolean) {
    this.visibility = if (isVisible) VISIBLE else GONE
}

fun View.changeVisibleInvisible() {
    this.visibility = if (this.visibility == VISIBLE) INVISIBLE else VISIBLE
}

fun View.changeVisibleGone() {
    this.visibility = if (this.visibility == VISIBLE) GONE else VISIBLE
}