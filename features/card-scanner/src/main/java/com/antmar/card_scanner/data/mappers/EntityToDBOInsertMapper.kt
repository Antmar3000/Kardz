package com.antmar.card_scanner.data.mappers

import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.local_database.data.entity.CardDBO

fun CardUIEntity.toDBOInsert() : CardDBO {
    return CardDBO (
        name = this.name,
        code = this.code,
        color = this.color,
        isBarcode = this.isBarcode
    )
}