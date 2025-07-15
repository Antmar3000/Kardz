package com.antmar.single_card_preview.data.repository

import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.local_database.data.database.DAOs.EditCardDao
import com.antmar.single_card_preview.data.mappers.toDBO
import com.antmar.single_card_preview.domain.SingleCardRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SingleCardRepositoryImpl(
    private val dao: EditCardDao
) : SingleCardRepository {

    override suspend fun updateCard(card: CardUIEntity) = dao.updateCard(card.toDBO())

    override suspend fun deleteCard(id: Int) = dao.deleteCard(id)

}