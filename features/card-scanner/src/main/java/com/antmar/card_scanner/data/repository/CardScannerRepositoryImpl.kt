package com.antmar.card_scanner.data.repository

import android.util.Log
import com.antmar.card_scanner.data.mappers.toDBOInsert
import com.antmar.card_scanner.data.mappers.toDBOUpdate
import com.antmar.card_scanner.data.mappers.toEntity
import com.antmar.card_scanner.domain.CardScannerRepository
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.local_database.data.database.DAOs.InsertCardDao
import com.antmar.local_database.data.entity.CardDBO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class CardScannerRepositoryImpl (
    private val dao : InsertCardDao
) : CardScannerRepository {

    override suspend fun insertCard(card: CardUIEntity) {
        dao.insertCard(card.toDBOInsert())
    }

    override suspend fun updateCard(card: CardUIEntity) {
        dao.updateCard(card.toDBOUpdate())
    }

    override suspend fun getCard(id: Int) : Flow<CardUIEntity?> {
        return dao.getCard(id).map { it?.toEntity() }
    }
}