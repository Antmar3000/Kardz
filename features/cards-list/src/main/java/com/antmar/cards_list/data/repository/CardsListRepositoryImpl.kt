package com.antmar.cards_list.data.repository

import android.util.Log
import com.antmar.cards_list.data.mappers.toEntity
import com.antmar.cards_list.domain.CardsListRepository
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.local_database.data.database.DAOs.CardsListDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class CardsListRepositoryImpl (
    private val dao : CardsListDao
) : CardsListRepository {

    override fun getAllCards() : Flow<List<CardUIEntity>> {
        return dao.getAllCards().map { list ->
            list.map { dbo -> dbo.toEntity() } }
    }

    override suspend fun deleteCard(id : Int) = dao.deleteCard(id)

}