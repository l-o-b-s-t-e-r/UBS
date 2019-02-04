package com.lobster.usb.data

import com.lobster.usb.domain.dto.SymbolCodeDto
import com.lobster.usb.domain.wrappers.SymbolsWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IexApi {

    @GET("ref-data/symbols")
    fun getSymbolCodes(): Single<List<SymbolCodeDto>>

    @GET("stock/market/batch")
    fun getSymbols(@Query("symbols") symbols: String, @Query("types") quote: String = "quote"): Single<SymbolsWrapper>

}