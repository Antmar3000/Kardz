package com.antmar.single_card_preview.data.mappers

import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.local_database.data.entity.CardDBO

fun CardUIEntity.toDBO() : CardDBO {
    return CardDBO (
        id = this.id,
        name = this.name,
        code = this.code,
        color = this.color
    )
}

fun CardDBO.toEntity () : CardUIEntity {
    return CardUIEntity (
        id = this.id,
        name = this.name,
        code = this.code,
        color = this.color
    )
}