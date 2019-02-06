package com.lobster.usb.domain.usecases.base

import com.lobster.usb.utils.RxUtils
import io.reactivex.Single
import io.reactivex.SingleTransformer


abstract class UseCaseSingle<T, P : UseCaseParameters> : DisposableHandler() {

    protected abstract fun buildUseCase(params: P): Single<T>

    fun execute(observer: CustomSingleObserver<T>, transformer: SingleTransformer<T, T>? = null, params: P = UseCaseParameters() as P) {
        var observable = buildUseCase(params)
            .compose(RxUtils.applyBgSchedulersSng())

        transformer?.let {
            observable = observable.compose(it)
        }

        addDisposable(observable.subscribeWith(observer))
    }

}