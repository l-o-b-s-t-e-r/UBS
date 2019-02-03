package com.lobster.usb.presentation.presenters

import com.lobster.usb.presentation.presenters.base.IBasePresenter

interface ISymbolsListPresenter {

    interface View : IBasePresenter.View {

    }

    abstract class Actions : IBasePresenter.Actions<ISymbolsListPresenter.View>() {

    }

}