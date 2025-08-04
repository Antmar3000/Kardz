package com.antmar.single_card_preview.domain

import com.antmar.core.domain.entity.CardUIEntity
import kotlinx.coroutines.flow.Flow

interface SingleCardRepository {

    suspend fun updateCard (card : CardUIEntity)

    suspend fun deleteCard (id : Int)

    suspend fun getCard (id : Int) : Flow<CardUIEntity?>

}