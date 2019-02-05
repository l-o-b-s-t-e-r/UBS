package com.lobster.usb.domain.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne
import java.util.*

@Entity
class SymbolNewsEntity(
    @Id
    var id: Long = 0,

    val image: String = "",

    val date: Date = Date(),

    val title: String = "",

    val description: String = "",

    val symbolCodes: String = "",

    var symbol: ToOne<SymbolEntity>? = null
)