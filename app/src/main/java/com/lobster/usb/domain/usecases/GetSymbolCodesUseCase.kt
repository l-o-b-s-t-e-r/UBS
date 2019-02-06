package com.lobster.usb.domain.usecases

import com.lobster.usb.domain.interfaces.ILocalRepository
import com.lobster.usb.domain.interfaces.IRemoteRepository
import com.lobster.usb.domain.mappers.Mapper
import com.lobster.usb.domain.usecases.base.UseCaseParameters
import com.lobster.usb.domain.usecases.base.UseCaseSingle
import io.reactivex.Single
import javax.inject.Inject

class GetSymbolCodesUseCase @Inject constructor(
    private val localRepository: ILocalRepository,
    private val remoteRepository: IRemoteRepository
) : UseCaseSingle<List<String>, UseCaseParameters>() {

    override fun buildUseCase(params: UseCaseParameters): Single<List<String>> {
        return localRepository.isEmpty()
            .flatMap { isEmpty ->
                if (isEmpty)
                    remoteRepository.getSymbolCodes()
                        .map { Mapper.symbolCodesToEntity(it) }
                        .flatMap { localRepository.saveSymbolCodes(it) }
                        .flatMap { localRepository.getAllSymbolCodes() }
                        .map { Mapper.symbolCodesToString(it) }
                else
                    localRepository.getAllSymbolCodes()
                        .map { Mapper.symbolCodesToString(it) }
            }
    }
}