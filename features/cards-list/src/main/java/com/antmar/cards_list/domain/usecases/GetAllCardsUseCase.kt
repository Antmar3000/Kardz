package com.antmar.cards_list.domain.usecases

import com.antmar.cards_list.domain.CardsListRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetAllCardsUseCase(
    private val repository: CardsListRepository
) {
    operator fun invoke() = repository.getAllCards()
}