package com.lobster.usb.domain.interfaces

import com.lobster.usb.domain.entities.SymbolCodeEntity
import com.lobster.usb.domain.entities.SymbolCompanyEntity
import com.lobster.usb.domain.entities.SymbolEntity
import com.lobster.usb.domain.entities.SymbolNewsEntity
import com.lobster.usb.domain.wrappers.SymbolsWrapper
import io.reactivex.Completable
import io.reactivex.Single

interface ILocalRepository {

    fun saveSymbolCodes(symbolCodes: List<SymbolCodeEntity>): Single<List<SymbolCodeEntity>>

    fun getAllSymbolCodes(): Single<List<SymbolCodeEntity>>

    fun getSymbolCodes(query: String, page: Int, limit: Int): Single<List<SymbolCodeEntity>>

    fun saveSymbols(symbols: SymbolsWrapper): Single<List<SymbolEntity>>

    fun getSymbolCompany(symbolId: Long): Single<SymbolCompanyEntity>

    fun getSymbolNews(symbolId: Long): Single<List<SymbolNewsEntity>>

    fun setSymbolAsFavorite(symbolId: Long, isFavorite: Boolean): Completable

    fun saveSymbolCompanyNews(symbol: String, companyEntity: SymbolCompanyEntity, newsEntities: List<SymbolNewsEntity>): Single<SymbolEntity>

    fun isEmpty(): Single<Boolean>

}