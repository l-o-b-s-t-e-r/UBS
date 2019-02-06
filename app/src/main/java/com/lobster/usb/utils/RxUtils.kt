package com.lobster.usb.utils

import com.lobster.usb.presentation.presenters.base.IBasePresenter
import io.reactivex.CompletableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object RxUtils {

    fun getNewCompositeSubIfUnsubscribed(disposable: CompositeDisposable?): CompositeDisposable {
        if (disposable == null || disposable.isDisposed) {
            return CompositeDisposable()
        }
        return disposable
    }

    fun <T> applyBgSchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applyBgSchedulersSng(): SingleTransformer<T, T> {
        return SingleTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun applyBgSchedulersCompl(): CompletableTransformer {
        return CompletableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T, V : IBasePresenter.View> applyLoadingSchedulers(view: V?): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.doOnSubscribe { view?.showLoading() }
                .doFinally { view?.hideLoading() }
        }
    }

    fun <T, V : IBasePresenter.View> applyLoadingSchedulersSng(view: V?): SingleTransformer<T, T> {
        return SingleTransformer { upstream ->
            upstream.doOnSubscribe {
                view?.showLoading()
            }.doFinally {
                view?.hideLoading()
            }
        }
    }

    fun <V : IBasePresenter.View> applyLoadingSchedulersCompl(view: V?): CompletableTransformer {
        return CompletableTransformer { upstream ->
            upstream.doOnSubscribe { view?.showLoading() }
                .doFinally { view?.hideLoading() }
        }
    }
}