package com.lobster.usb.domain.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
class SymbolEntity(
    val change: Double = 0.0,

    val lastPrice: Double = 0.0,

    var isFavorite: Boolean = false,

    @Id
    var id: Long = 0
) {

    var news: ToMany<SymbolNewsEntity>? = null

    var company: ToOne<SymbolCompanyEntity>? = null

    var code: ToOne<SymbolCodeEntity>? = null
}