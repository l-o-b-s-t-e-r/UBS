package com.lobster.usb.domain.adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.lobster.usb.domain.dto.SymbolDto
import com.lobster.usb.domain.wrappers.SymbolsWrapper


class SymbolTypeAdapter : TypeAdapter<SymbolsWrapper>() {

    override fun write(out: JsonWriter?, value: SymbolsWrapper?) {

    }

    override fun read(jsonReader: JsonReader): SymbolsWrapper {
        val symbolsDto = mutableListOf<SymbolDto>()
        var symbolCode: String = ""
        var companyName: String = ""
        var change: Double = 0.0
        var lastPrice: Double = 0.0

        jsonReader.beginObject()
        while (jsonReader.hasNext()) {
            jsonReader.nextName()
            jsonReader.beginObject()
            while (jsonReader.hasNext()) {
                jsonReader.nextName()
                jsonReader.beginObject()
                while (jsonReader.hasNext()) {
                    when (jsonReader.nextName()) {
                        "symbol" -> symbolCode = jsonReader.nextString()
                        "companyName" -> companyName = jsonReader.nextString()
                        "change" -> change = jsonReader.nextDouble()
                        "lastPrice" -> lastPrice = jsonReader.nextDouble()
                        else -> jsonReader.skipValue()
                    }
                }
                jsonReader.endObject()
            }
            jsonReader.endObject()

            symbolsDto.add(SymbolDto(symbolCode, companyName, change, lastPrice))
        }
        jsonReader.endObject()

        return SymbolsWrapper(symbolsDto)
    }
}