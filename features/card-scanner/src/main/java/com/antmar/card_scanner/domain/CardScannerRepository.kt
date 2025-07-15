package com.antmar.card_scanner.domain

import com.antmar.core.domain.entity.CardUIEntity

interface CardScannerRepository {

    suspend fun insertCard (card : CardUIEntity)
}