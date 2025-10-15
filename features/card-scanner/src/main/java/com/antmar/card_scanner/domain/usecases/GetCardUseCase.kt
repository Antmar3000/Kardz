package com.antmar.card_scanner.domain.usecases

import com.antmar.card_scanner.domain.CardScannerRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetCardUseCase (
    private val cardScannerRepository: CardScannerRepository
) {

    suspend operator fun invoke(id : Int) = cardScannerRepository.getCard(id)
}