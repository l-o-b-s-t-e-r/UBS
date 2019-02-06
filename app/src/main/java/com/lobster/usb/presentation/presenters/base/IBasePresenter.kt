package com.lobster.usb.presentation.presenters.base

interface IBasePresenter {

    interface View {
        fun showLoading()

        fun hideLoading()
    }

    open class Actions<T : IBasePresenter.View> {
        var view: T? = null

        fun bindView(view: T) {
            this.view = view
        }

        fun unbindView() {
            this.view = null
        }

        fun dispose() {

        }
    }

}
