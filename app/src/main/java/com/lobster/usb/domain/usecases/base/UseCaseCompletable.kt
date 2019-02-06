package com.lobster.usb.domain.usecases.base

import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


abstract class UseCaseCompletable<P : UseCaseParameters>() : DisposableHandler() {
    protected abstract fun buildUseCase(params: P): Completable

    fun execute(observer: CustomCompletableObserver, transformer: CompletableTransformer? = null, params: P = UseCaseParameters() as P) {
        var observable = buildUseCase(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        transformer?.let {
            observable = observable.compose(it)
        }

        addDisposable(observable.subscribeWith(observer))
    }
}