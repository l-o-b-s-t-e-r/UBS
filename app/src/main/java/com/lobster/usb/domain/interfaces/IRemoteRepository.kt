package com.lobster.usb.domain.interfaces

import com.lobster.usb.domain.dto.SymbolCodeDto
import com.lobster.usb.domain.dto.SymbolCompanyDto
import com.lobster.usb.domain.dto.SymbolNewsDto
import com.lobster.usb.domain.wrappers.SymbolsWrapper
import io.reactivex.Single

interface IRemoteRepository {

    fun getSymbolCodes(): Single<List<SymbolCodeDto>>

    fun getSymbols(symbols: String): Single<SymbolsWrapper>

    fun getSymbolCompany(symbol: String): Single<SymbolCompanyDto>

    fun getSymbolNews(symbol: String): Single<List<SymbolNewsDto>>
}