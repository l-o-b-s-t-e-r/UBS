package com.lobster.usb.domain.converters

import io.objectbox.converter.PropertyConverter

class RelatedConverter : PropertyConverter<List<String>, String> {

    override fun convertToDatabaseValue(entityProperty: List<String>?): String {
        val iMax = entityProperty?.lastIndex ?: 0
        val b = StringBuilder()
        var i = 0
        while (true) {
            b.append(entityProperty?.get(i) ?: "")
            if (i == iMax)
                return b.toString()
            b.append(",")
            i++
        }
    }

    override fun convertToEntityProperty(databaseValue: String?): List<String> {
        return databaseValue?.split(",") ?: listOf()
    }

}