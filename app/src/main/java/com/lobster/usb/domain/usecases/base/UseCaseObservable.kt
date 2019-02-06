package com.lobster.usb.domain.usecases.base

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


abstract class UseCaseObservable<T, P : UseCaseParameters> : DisposableHandler() {
    protected abstract fun buildUseCase(params: P): Observable<T>

    private fun execute(observer: CustomObserver<T>, transformer: ObservableTransformer<T, T>? = null, params: P = UseCaseParameters() as P) {
        var observable = buildUseCase(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        transformer?.let {
            observable = observable.compose(it)
        }

        addDisposable(observable.subscribeWith(observer))
    }
}