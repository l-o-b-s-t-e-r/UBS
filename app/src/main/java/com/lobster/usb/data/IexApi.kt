package com.lobster.usb.data

import com.lobster.usb.domain.dto.SymbolCodeDto
import com.lobster.usb.domain.dto.SymbolCompanyDto
import com.lobster.usb.domain.dto.SymbolNewsDto
import com.lobster.usb.domain.wrappers.SymbolsWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IexApi {

    @GET("ref-data/symbols")
    fun getSymbolCodes(): Single<List<SymbolCodeDto>>

    @GET("stock/market/batch")
    fun getSymbols(@Query("symbols") symbols: String, @Query("types") quote: String = "quote"): Single<SymbolsWrapper>

    @GET("stock/{symbol}/company")
    fun getSymbolCompany(@Path("symbol") symbol: String): Single<SymbolCompanyDto>

    @GET("stock/{symbol}/news")
    fun getCompanyNews(@Path("symbol") symbol: String): Single<List<SymbolNewsDto>>

}