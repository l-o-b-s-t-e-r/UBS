package com.lobster.usb.data

import com.lobster.usb.domain.interfaces.IRemoteRepository

class RemoteRepository(private val api: IexApi) : IRemoteRepository {

    override fun getSymbolCodes() = api.getSymbolCodes()

    override fun getSymbols(symbols: String) = api.getSymbols(symbols)

}