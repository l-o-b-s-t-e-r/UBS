package com.lobster.usb.domain.pojo

import java.util.*

class SymbolNews(
    val image: String,

    val date: Date,

    val title: String,

    val description: String,

    val symbolCodes: List<String>
)