package com.lobster.usb.domain.usecases.base

import io.reactivex.observers.DisposableCompletableObserver

/**
 * Created by Lobster on 27/10/18.
 */

class CustomCompletableObserver : DisposableCompletableObserver() {

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }
}
