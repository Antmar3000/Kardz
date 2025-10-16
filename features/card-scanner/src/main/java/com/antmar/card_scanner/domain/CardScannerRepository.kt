package com.antmar.card_scanner.domain

import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.local_database.data.entity.CardDBO
import kotlinx.coroutines.flow.Flow

interface CardScannerRepository {

    suspend fun insertCard (card : CardUIEntity)

    suspend fun updateCard (card : CardUIEntity)

    suspend fun getCard (id : Int) : Flow<CardUIEntity?>
}