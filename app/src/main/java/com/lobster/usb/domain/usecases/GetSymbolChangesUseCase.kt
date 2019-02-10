package com.lobster.usb.domain.usecases

import com.lobster.usb.domain.interfaces.ILocalRepository
import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.domain.usecases.base.UseCaseObservable
import com.lobster.usb.domain.usecases.base.UseCaseParameters
import io.reactivex.Observable
import javax.inject.Inject

class GetSymbolChangesUseCase @Inject constructor(
    private val localRepository: ILocalRepository
) : UseCaseObservable<Symbol, GetSymbolChangesUseCase.Params>() {

    override fun buildUseCase(params: Params): Observable<Symbol> {
        return localRepository.getSymbolChangeListener(params.symbol.id, observer)
    }

    class Params(val symbol: Symbol) : UseCaseParameters()
}