package com.lobster.usb.domain.usecases.base

import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


abstract class UseCaseCompletable<in P>() : DisposableHandler() {

    private lateinit var observer: CustomCompletableObserver

    protected abstract fun buildUseCase(params: P): Completable

    fun execute(observer: CustomCompletableObserver, params: P) {
        execute(observer, null, params)
    }

    fun execute(observer: CustomCompletableObserver, transformer: CompletableTransformer?, params: P) {
        this.observer = observer
        addDisposable(
            this.buildUseCase(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }
}