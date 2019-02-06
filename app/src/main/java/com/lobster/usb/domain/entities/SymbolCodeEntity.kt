package com.lobster.usb.domain.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
class SymbolCodeEntity(
    @Unique
    val code: String = "",

    @Id
    var id: Long = 0
)