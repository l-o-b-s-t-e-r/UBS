package com.lobster.usb.domain.dto

data class SymbolDto(
    val symbolCode: String = "",
    val companyName: String = "",
    val change: Double = 0.0,
    val lastPrice: Double = 0.0
)