package com.lobster.usb.data

import com.lobster.usb.domain.interfaces.IRemoteRepository

class RemoteRepository(private val api: IexApi) : IRemoteRepository {

    override fun getSymbolCodes() = api.getSymbolCodes()

    override fun getSymbols(symbols: String) = api.getSymbols(symbols)

    override fun getSymbolCompany(symbol: String) = api.getSymbolCompany(symbol)

    override fun getSymbolNews(symbol: String) = api.getCompanyNews(symbol)

}