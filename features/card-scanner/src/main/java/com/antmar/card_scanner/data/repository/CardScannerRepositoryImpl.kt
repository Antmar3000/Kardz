package com.antmar.card_scanner.data.repository

import android.util.Log
import com.antmar.card_scanner.data.mappers.toDBOInsert
import com.antmar.card_scanner.domain.CardScannerRepository
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.local_database.data.database.DAOs.InsertCardDao
import me.tatarka.inject.annotations.Inject

@Inject
class CardScannerRepositoryImpl (
    private val dao : InsertCardDao
) : CardScannerRepository {
    override suspend fun insertCard(card: CardUIEntity) {
        dao.insertCard(card.toDBOInsert())
    }
}