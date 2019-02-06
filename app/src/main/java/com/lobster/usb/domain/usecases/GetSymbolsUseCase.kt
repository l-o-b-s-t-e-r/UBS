package com.lobster.usb.domain.usecases

import com.lobster.usb.domain.entities.SymbolCodeEntity
import com.lobster.usb.domain.interfaces.ILocalRepository
import com.lobster.usb.domain.interfaces.IRemoteRepository
import com.lobster.usb.domain.mappers.Mapper
import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.domain.usecases.base.UseCaseParameters
import com.lobster.usb.domain.usecases.base.UseCaseSingle
import io.reactivex.Single
import javax.inject.Inject

class GetSymbolsUseCase @Inject constructor(
    private val localRepository: ILocalRepository,
    private val remoteRepository: IRemoteRepository
) : UseCaseSingle<List<Symbol>, GetSymbolsUseCase.Params>() {

    override fun buildUseCase(params: Params): Single<List<Symbol>> {
        return localRepository.isEmpty()
            .flatMap { isEmpty ->
                if (isEmpty)
                    remoteRepository.getSymbolCodes()
                        .map { Mapper.symbolCodesToEntity(it) }
                        .flatMap { localRepository.saveSymbolCodes(it) }
                        .flatMap { localRepository.getSymbolCodes(params.query, params.page, params.limit) }
                else
                    localRepository.getSymbolCodes(params.query, params.page, params.limit)
            }
            .flatMap { remoteRepository.getSymbols(symbolsToString(it)) }
            .flatMap { localRepository.saveSymbols(it) }
            .map { Mapper.symbolEntitiesToSymbols(it) }
    }

    private fun symbolsToString(symbols: List<SymbolCodeEntity>): String {
        val iMax = symbols.lastIndex
        val b = StringBuilder()
        var i = 0
        while (true) {
            b.append(symbols[i].code)
            if (i == iMax)
                return b.toString()
            b.append(",")
            i++
        }
    }

    class Params(val query: String, val page: Int, val limit: Int) : UseCaseParameters()
}