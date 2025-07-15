package com.antmar.single_card_preview.domain.usecases

import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.single_card_preview.domain.SingleCardRepository
import me.tatarka.inject.annotations.Inject

@Inject
class EditCardUseCase (
    private val repository : SingleCardRepository
) {
    suspend operator fun invoke (card : CardUIEntity) = repository.updateCard(card)
}