package com.lobster.usb.domain.usecases.base

import io.reactivex.observers.DisposableSingleObserver

/**
 * Created by Lobster on 27/10/18.
 */

class CustomSingleObserver<T> : DisposableSingleObserver<T>() {

    override fun onSuccess(t: T) {

    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

}
