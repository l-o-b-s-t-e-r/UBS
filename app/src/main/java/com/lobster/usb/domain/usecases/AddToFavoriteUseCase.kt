package com.lobster.usb.domain.usecases

import com.lobster.usb.domain.interfaces.ILocalRepository
import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.domain.usecases.base.UseCaseCompletable
import com.lobster.usb.domain.usecases.base.UseCaseParameters
import io.reactivex.Completable
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(private val localRepository: ILocalRepository) :
    UseCaseCompletable<AddToFavoriteUseCase.Params>() {

    override fun buildUseCase(params: Params): Completable {
        return localRepository.setSymbolAsFavorite(params.symbol.id, params.isFavorite)
    }

    class Params(val symbol: Symbol, val isFavorite: Boolean) : UseCaseParameters()
}