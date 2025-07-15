package com.antmar.cards_list.data.mappers

import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.local_database.data.entity.CardDBO

fun CardDBO.toEntity () : CardUIEntity {
    return CardUIEntity (
        id = this.id,
        name = this.name,
        code = this.code,
        color = this.color
    )
}


