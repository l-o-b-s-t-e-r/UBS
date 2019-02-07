package com.lobster.usb.domain.dto

import java.util.*

data class SymbolNewsDto(
    val datetime: Date = Date(),
    val source: String = "",
    val headline: String = "",
    val summary: String = "",
    val related: String = "",
    val image: String = ""
)