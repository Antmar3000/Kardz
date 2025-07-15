package com.antmar.cards_list.domain.usecases

import com.antmar.cards_list.domain.CardsListRepository
import me.tatarka.inject.annotations.Inject

@Inject
class DeleteCardUseCase (
    private val repository : CardsListRepository
){
    suspend operator fun invoke(id : Int) = repository.deleteCard(id)
}