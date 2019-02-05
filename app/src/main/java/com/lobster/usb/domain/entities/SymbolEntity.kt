package com.lobster.usb.domain.entities

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
class SymbolEntity(
    @Id
    var id: Long = 0,

    var symbol: ToOne<SymbolEntity>? = null,

    var company: ToOne<SymbolCompanyEntity>? = null,

    val change: Double = 0.0,

    val lastPrice: Double = 0.0,

    val isFavorite: Boolean = false,

    @Backlink(to = "symbol")
    var news: ToMany<SymbolNewsEntity>? = null
)