package com.lobster.usb.domain.usecases.base

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


abstract class UseCaseObservable<T, P : UseCaseParameters> : DisposableHandler() {

    protected lateinit var observer: Observer<T>

    protected abstract fun buildUseCase(params: P): Observable<T>

    fun execute(observer: CustomObserver<T>, transformer: ObservableTransformer<T, T>? = null, params: P = UseCaseParameters() as P) {
        this.observer = observer
        var observable = buildUseCase(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        transformer?.let {
            observable = observable.compose(it)
        }

        addDisposable(observable.subscribeWith(observer))
    }

    fun execute(transformer: ObservableTransformer<T, T>? = null, params: P = UseCaseParameters() as P) {
        execute(CustomObserver(), transformer, params)
    }

    fun execute(observer: CustomObserver<T>, params: P = UseCaseParameters() as P) {
        execute(observer, null, params)
    }

    fun execute(params: P = UseCaseParameters() as P) {
        execute(CustomObserver(), null, params)
    }

    fun execute() {
        execute(CustomObserver(), null)
    }
}