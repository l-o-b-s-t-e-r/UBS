package com.lobster.usb.domain.usecases

import com.lobster.usb.domain.interfaces.ILocalRepository
import com.lobster.usb.domain.mappers.Mapper
import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.domain.usecases.base.UseCaseParameters
import com.lobster.usb.domain.usecases.base.UseCaseSingle
import io.reactivex.Single
import javax.inject.Inject

class GetSymbolUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) : UseCaseSingle<Symbol, GetSymbolUseCase.Params>() {

    override fun buildUseCase(params: Params): Single<Symbol> {
        return localRepository.getSymbol(params.symbol.id)
            .map { Mapper.symbolEntityToSymbol(it) }
    }

    class Params(val symbol: Symbol) : UseCaseParameters()
}