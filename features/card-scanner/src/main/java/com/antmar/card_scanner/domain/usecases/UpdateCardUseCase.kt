package com.antmar.card_scanner.domain.usecases

import com.antmar.card_scanner.domain.CardScannerRepository
import com.antmar.core.domain.entity.CardUIEntity
import me.tatarka.inject.annotations.Inject

@Inject
class UpdateCardUseCase (
    private val repository: CardScannerRepository
) {
    suspend operator fun invoke(
        id: Int,
        name: String,
        code: String,
        color: Long,
        isBarcode: Boolean
    ) {
        val card =
            CardUIEntity(id = id, name = name, code = code, color = color, isBarcode = isBarcode)

        repository.updateCard(card)
    }
}