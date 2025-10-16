package com.example.push_messaging.data.mappers

import com.antmar.local_database.data.entity.CardDBO
import com.example.push_messaging.domain.entity.CardPushMessageEntity

fun CardPushMessageEntity.toDBO () : CardDBO {
    return CardDBO(
        name = this.name,
        code = this.code,
        color = this.color,
        isBarcode = this.isBarcode
    )
}