package com.antmar.cards_list.domain

import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.local_database.data.entity.CardDBO
import kotlinx.coroutines.flow.Flow

interface CardsListRepository {

    fun getAllCards() : Flow<List<CardUIEntity>>

    suspend fun deleteCard(id : Int)

}