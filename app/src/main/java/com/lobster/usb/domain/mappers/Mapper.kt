package com.lobster.usb.domain.mappers

import com.lobster.usb.domain.dto.SymbolCodeDto
import com.lobster.usb.domain.dto.SymbolDto
import com.lobster.usb.domain.entities.SymbolCodeEntity
import com.lobster.usb.domain.entities.SymbolEntity
import com.lobster.usb.domain.pojo.Symbol

object Mapper {

    fun symbolCodesToEntity(symbolCodes: List<SymbolCodeDto>): List<SymbolCodeEntity> {
        val symbolCodeEntities = mutableListOf<SymbolCodeEntity>()
        symbolCodes.forEach {
            symbolCodeEntities.add(symbolCodeToEntity(it))
        }

        return symbolCodeEntities
    }

    fun symbolCodeToEntity(symbolCodeDto: SymbolCodeDto): SymbolCodeEntity {
        return SymbolCodeEntity(symbolCodeDto.symbol)
    }

    fun symbolsToEntity(symbolCodes: List<SymbolCodeDto>): List<SymbolCodeEntity> {
        val symbolCodeEntities = mutableListOf<SymbolCodeEntity>()
        symbolCodes.forEach {
            symbolCodeEntities.add(symbolCodeToEntity(it))
        }

        return symbolCodeEntities
    }

    fun symbolToEntity(symbolDto: SymbolDto): SymbolEntity {
        return SymbolEntity(symbolDto.change, symbolDto.lastPrice)
    }

    fun symbolEntitiesToSymbols(symbolEntities: List<SymbolEntity>): List<Symbol> {
        val symbols = mutableListOf<Symbol>()
        symbolEntities.forEach {
            symbols.add(
                Symbol(
                    it.id,
                    it.code!!.target.code,
                    it.company!!.target.companyName,
                    it.change,
                    it.lastPrice,
                    it.isFavorite
                )
            )
        }

        return symbols
    }

    fun symbolCodesToString(symbolCodes: List<SymbolCodeEntity>): List<String> {
        val codes = mutableListOf<String>()
        symbolCodes.forEach {
            codes.add(it.code)
        }

        return codes
    }
}