package com.lobster.usb.domain.usecases.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Created by vlad on 10/17/17.
 */
open class DisposableHandler {

    private var disposables = CompositeDisposable()

    fun dispose() {
        getCompositeDisposable().dispose()
    }

    private fun getCompositeDisposable(): CompositeDisposable {
        if (disposables.isDisposed) {
            disposables = CompositeDisposable()
        }

        return disposables
    }

    protected fun addDisposable(disposable: Disposable) {
        getCompositeDisposable().add(disposable)
    }
}