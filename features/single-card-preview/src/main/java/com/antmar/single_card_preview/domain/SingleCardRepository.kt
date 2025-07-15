package com.antmar.single_card_preview.domain

import com.antmar.core.domain.entity.CardUIEntity

interface SingleCardRepository {

    suspend fun updateCard (card : CardUIEntity)

    suspend fun deleteCard (id : Int)

}