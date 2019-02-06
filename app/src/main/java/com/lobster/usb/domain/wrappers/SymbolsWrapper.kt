package com.lobster.usb.domain.wrappers

import com.lobster.usb.domain.dto.SymbolDto

data class SymbolsWrapper(
    val symbolCodes: MutableMap<String, SymbolDto>
)