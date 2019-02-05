package com.lobster.usb.domain.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
class SymbolCodeEntity(
    @Id var id: Long = 0,

    @Unique
    val code: String = ""
)