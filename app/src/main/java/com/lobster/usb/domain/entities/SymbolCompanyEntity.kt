package com.lobster.usb.domain.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class SymbolCompanyEntity(
    val companyName: String = "",

    val ceo: String = "",

    val description: String = "",

    val website: String = "",

    @Id
    var id: Long = 0
)