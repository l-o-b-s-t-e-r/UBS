package com.lobster.usb.domain.entities

import com.lobster.usb.domain.converters.RelatedConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
class SymbolNewsEntity(
    val image: String = "",

    val date: Date = Date(),

    val source: String = "",

    val title: String = "",

    val description: String = "",

    @Convert(converter = RelatedConverter::class, dbType = String::class)
    val symbolCodes: List<String> = listOf(),

    @Id
    var id: Long = 0
)