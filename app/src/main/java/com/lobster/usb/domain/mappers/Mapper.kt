package com.lobster.usb.domain.mappers

import com.lobster.usb.domain.dto.SymbolCodeDto
import com.lobster.usb.domain.dto.SymbolCompanyDto
import com.lobster.usb.domain.dto.SymbolDto
import com.lobster.usb.domain.dto.SymbolNewsDto
import com.lobster.usb.domain.entities.SymbolCodeEntity
import com.lobster.usb.domain.entities.SymbolCompanyEntity
import com.lobster.usb.domain.entities.SymbolEntity
import com.lobster.usb.domain.entities.SymbolNewsEntity
import com.lobster.usb.domain.pojo.Symbol
import com.lobster.usb.domain.pojo.SymbolCompany
import com.lobster.usb.domain.pojo.SymbolNews

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
            symbols.add(symbolEntityToSymbol(it))
        }

        return symbols
    }

    fun symbolEntityToSymbol(symbolEntity: SymbolEntity): Symbol {
        return Symbol(
            symbolEntity.id,
            symbolEntity.code!!.target.code,
            symbolEntity.company!!.target.companyName,
            symbolEntity.change,
            symbolEntity.lastPrice,
            symbolEntity.isFavorite
        )
    }

    fun symbolEntityToSymbolCompany(symbolEntity: SymbolEntity): SymbolCompany {
        return SymbolCompany(
            symbolEntityToSymbol(symbolEntity),
            symbolEntity.company!!.target.companyName,
            symbolEntity.change,
            symbolEntity.company!!.target.ceo,
            symbolEntity.company!!.target.description,
            symbolEntity.company!!.target.website,
            symbolNewsEntityToNews(symbolEntity.news?.toList() ?: listOf())
        )
    }


    fun symbolCodesToString(symbolCodes: List<SymbolCodeEntity>): List<String> {
        val codes = mutableListOf<String>()
        symbolCodes.forEach {
            codes.add(it.code)
        }

        return codes
    }

    fun symbolCompanyToEntity(symbolCompanyDto: SymbolCompanyDto): SymbolCompanyEntity {
        return SymbolCompanyEntity(
            symbolCompanyDto.companyName,
            symbolCompanyDto.CEO,
            symbolCompanyDto.description,
            symbolCompanyDto.website
        )
    }

    fun symbolNewsToEntity(symbolNewsDtos: List<SymbolNewsDto>): List<SymbolNewsEntity> {
        val news = mutableListOf<SymbolNewsEntity>()
        symbolNewsDtos.forEach {
            news.add(
                SymbolNewsEntity(
                    it.image,
                    it.datetime,
                    it.source,
                    it.headline,
                    it.summary,
                    it.related.split(",")
                )
            )
        }

        return news
    }

    fun symbolNewsEntityToNews(symbolNewsEntities: List<SymbolNewsEntity>): List<SymbolNews> {
        val news = mutableListOf<SymbolNews>()
        symbolNewsEntities.forEach {
            news.add(
                SymbolNews(
                    it.image,
                    it.date,
                    it.source,
                    it.title,
                    it.description,
                    it.symbolCodes
                )
            )
        }

        return news
    }
}