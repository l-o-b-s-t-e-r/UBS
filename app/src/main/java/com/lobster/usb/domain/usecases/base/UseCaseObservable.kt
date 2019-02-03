package com.lobster.usb.domain.usecases.base

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


abstract class UseCaseObservable<T, in P> : DisposableHandler() {
    private lateinit var observer: CustomObserver<T>

    protected abstract fun buildUseCase(params: P): Observable<T>

    fun execute(observer: CustomObserver<T>, params: P) {
        execute(observer, null, params)
    }

    private fun execute(observer: CustomObserver<T>, transformer: ObservableTransformer<T, T>?, params: P) {
        this.observer = observer
        addDisposable(
            this.buildUseCase(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }
}