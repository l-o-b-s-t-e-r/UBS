package com.lobster.usb.domain.wrappers

import com.lobster.usb.domain.dto.SymbolCompanyDto
import com.lobster.usb.domain.dto.SymbolNewsDto

class SymbolCompanyWrapper(
    val symbolCompanyDto: SymbolCompanyDto = SymbolCompanyDto(),
    val symbolNewsDtos: List<SymbolNewsDto> = listOf()
)