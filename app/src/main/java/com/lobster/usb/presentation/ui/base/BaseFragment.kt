package com.lobster.usb.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lobster.usb.presentation.presenters.base.IBasePresenter

import javax.inject.Inject

abstract class BaseFragment<V : IBasePresenter.View, P : IBasePresenter.Actions<V>> : Fragment(), IBasePresenter.View {

    @Inject
    lateinit var presenter: P

    abstract fun inject()

    abstract fun layoutId(): Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(layoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this as V)
    }

    override fun onDestroyView() {
        presenter.unbindView()
        presenter.dispose()
        super.onDestroyView()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}
