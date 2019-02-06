package com.lobster.usb.domain.pojo

data class SymbolCompany(
    val symbol: Symbol,

    val companyName: String,

    val change: Double,

    val ceo: String,

    val description: String,

    val website: String,

    val news: List<SymbolNews>
)