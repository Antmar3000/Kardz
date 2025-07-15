package com.antmar.card_scanner.domain.usecases

import com.antmar.card_scanner.data.repository.CardScannerRepositoryImpl
import com.antmar.card_scanner.domain.CardScannerRepository
import com.antmar.core.domain.entity.CardUIEntity
import me.tatarka.inject.annotations.Inject

@Inject
class InsertCardUseCase (
    private val repository: CardScannerRepository
){
    suspend operator fun invoke (card : CardUIEntity) = repository.insertCard(card)
}