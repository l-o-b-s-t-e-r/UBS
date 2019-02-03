package com.lobster.usb.domain.usecases.base

import io.reactivex.observers.DisposableObserver

/**
 * Created by Lobster on 27/10/18.
 */

class CustomObserver<T> : DisposableObserver<T>() {

    override fun onStart() {

    }

    override fun onNext(t: T) {

    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

    override fun onComplete() {

    }
}
