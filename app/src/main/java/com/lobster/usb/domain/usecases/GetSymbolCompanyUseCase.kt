package com.lobster.usb.domain.usecases

import com.lobster.usb.domain.dto.SymbolCompanyDto
import com.lobster.usb.domain.dto.SymbolNewsDto
import com.lobster.usb.domain.interfaces.ILocalRepository
import com.lobster.usb.domain.interfaces.IRemoteRepository
import com.lobster.usb.domain.mappers.Mapper
import com.lobster.usb.domain.pojo.SymbolCompany
import com.lobster.usb.domain.usecases.base.UseCaseParameters
import com.lobster.usb.domain.usecases.base.UseCaseSingle
import com.lobster.usb.domain.wrappers.SymbolCompanyWrapper
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetSymbolCompanyUseCase @Inject constructor(
    private val localRepository: ILocalRepository,
    private val remoteRepository: IRemoteRepository
) : UseCaseSingle<SymbolCompany, GetSymbolCompanyUseCase.Params>() {

    override fun buildUseCase(params: Params): Single<SymbolCompany> {
        return Single.zip(
            remoteRepository.getSymbolCompany(params.symbol),
            remoteRepository.getSymbolNews(params.symbol),
            BiFunction<SymbolCompanyDto, List<SymbolNewsDto>, SymbolCompanyWrapper> { company, news ->
                SymbolCompanyWrapper(company, news)
            })
            .flatMap { localRepository.saveSymbolCompanyNews(params.symbol, Mapper.symbolCompanyToEntity(it.symbolCompanyDto), Mapper.symbolNewsToEntity(it.symbolNewsDtos)) }
            .map { Mapper.symbolEntityToSymbolCompany(it) }
    }

    class Params(val symbol: String) : UseCaseParameters()
}