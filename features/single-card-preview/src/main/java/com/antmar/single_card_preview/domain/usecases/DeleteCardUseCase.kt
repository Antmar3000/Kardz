package com.antmar.single_card_preview.domain.usecases

import com.antmar.single_card_preview.domain.SingleCardRepository
import me.tatarka.inject.annotations.Inject

@Inject
class DeleteCardUseCase (
    private val repository : SingleCardRepository
) {
    suspend operator fun invoke (id : Int) = repository.deleteCard(id)
}