package com.lobster.usb.domain.usecases.base

import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


abstract class UseCaseSingle<T, in P> : DisposableHandler() {

    private lateinit var observer: CustomSingleObserver<T>

    protected abstract fun buildUseCase(params: P): Single<T>

    fun execute(observer: CustomSingleObserver<T>, params: P) {
        execute(observer, null, params)
    }

    fun execute(observer: CustomSingleObserver<T>, transformer: SingleTransformer<T, T>?, params: P) {
        this.observer = observer
        addDisposable(
            this.buildUseCase(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }

}