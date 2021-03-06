package com.lobster.usb.data

import com.lobster.usb.domain.entities.*
import com.lobster.usb.domain.interfaces.ILocalRepository
import com.lobster.usb.domain.mappers.Mapper
import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.domain.wrappers.SymbolsWrapper
import io.objectbox.Box
import io.objectbox.BoxStore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single


class LocalRepository(private val boxStore: BoxStore) : ILocalRepository {

    var boxSymbol: Box<SymbolEntity> = boxStore.boxFor(SymbolEntity::class.java)
    var boxSymbolCode: Box<SymbolCodeEntity> = boxStore.boxFor(SymbolCodeEntity::class.java)
    var boxSymbolCompany: Box<SymbolCompanyEntity> = boxStore.boxFor(SymbolCompanyEntity::class.java)
    var boxSymbolNews: Box<SymbolNewsEntity> = boxStore.boxFor(SymbolNewsEntity::class.java)

    override fun saveSymbolCodes(symbolCodes: List<SymbolCodeEntity>): Single<List<SymbolCodeEntity>> {
        return Single.fromCallable {
            boxSymbolCode.put(symbolCodes)
            boxSymbolCode.all
        }
    }

    override fun getAllSymbolCodes(): Single<List<SymbolCodeEntity>> {
        return Single.just(boxSymbolCode.all)
    }

    override fun getSymbolCodes(query: String, page: Int, limit: Int): Single<List<SymbolCodeEntity>> {
        return Single.just(
            boxSymbolCode.query()
                .startsWith(SymbolCodeEntity_.code, query)
                .build()
                .find(page * limit.toLong(), limit.toLong())
        )
    }

    override fun saveSymbols(symbols: SymbolsWrapper): Single<List<SymbolEntity>> {
        return Single.fromCallable {
            val symbolsEntities = mutableListOf<SymbolEntity>()
            symbols.symbolCodes.forEach { symbol ->
                val symbolEntity = Mapper.symbolToEntity(symbol.value)
                symbolEntity.code!!.target = getSymbolCode(symbol.key)
                symbolEntity.company!!.target = SymbolCompanyEntity(symbol.value.companyName)
                getSymbolByCode(symbol.key)?.let {
                    symbolEntity.id = it.id
                    symbolEntity.isFavorite = it.isFavorite
                }

                boxSymbol.put(symbolEntity)
                symbolsEntities.add(symbolEntity)
            }

            symbolsEntities
        }
    }

    override fun saveSymbolCompanyNews(
        symbol: String,
        companyEntity: SymbolCompanyEntity,
        newsEntities: List<SymbolNewsEntity>
    ): Single<SymbolEntity> {
        return Single.fromCallable {
            val symbolEntity = getSymbolByCode(symbol)!!.apply {
                company!!.target = companyEntity
                news!!.clear()
                news!!.addAll(newsEntities)
            }

            boxSymbol.put(symbolEntity)
            symbolEntity
        }
    }

    override fun getSymbolCompany(symbolId: Long): Single<SymbolCompanyEntity> {
        return Single.just(boxSymbolCompany.get(symbolId))
    }

    override fun getSymbolNews(symbolId: Long): Single<List<SymbolNewsEntity>> {
        return Single.just(boxSymbol.get(symbolId).news)
    }

    override fun setSymbolAsFavorite(symbolId: Long, isFavorite: Boolean): Completable {
        return Completable.fromAction {
            val symbolEntity = boxSymbol.get(symbolId).also { it.isFavorite = isFavorite }
            boxSymbol.put(symbolEntity)
        }
    }

    override fun getSymbolChangeListener(symbolId: Long, observer: Observer<Symbol>): Observable<Symbol> {
        return Observable.create<Symbol> { emitter ->
            boxSymbol.query()
                .equal(SymbolEntity_.id, symbolId)
                .build()
                .subscribe()
                .onlyChanges()
                .observer { data ->
                    emitter.onNext(Mapper.symbolEntityToSymbol(data[0]))
                }
        }
    }

    override fun getSymbol(symbolId: Long) = Single.just(boxSymbol[symbolId])

    override fun isEmpty(): Single<Boolean> {
        return Single.just(boxSymbolCode.isEmpty)
    }

    private fun getSymbolCode(code: String) =
        boxSymbolCode.query()
            .equal(SymbolCodeEntity_.code, code)
            .build()
            .findUnique()

    private fun getSymbolByCode(code: String): SymbolEntity? {
        val builder = boxSymbol.query()
        builder.link(SymbolEntity_.code).equal(SymbolCodeEntity_.code, code)
        return builder.build().findFirst()
    }
}