package com.example.push_messaging.data.repository

import com.antmar.local_database.data.database.DAOs.InsertCardDao
import com.example.push_messaging.data.mappers.toDBO
import com.example.push_messaging.domain.PushMessagingRepository
import com.example.push_messaging.domain.entity.CardPushMessageEntity
import me.tatarka.inject.annotations.Inject

@Inject
class PushMessagingRepositoryImpl (
    private val dao: InsertCardDao
) : PushMessagingRepository {

    override suspend fun insertCard(card: CardPushMessageEntity) {
        dao.insertCard(card.toDBO())
    }
}