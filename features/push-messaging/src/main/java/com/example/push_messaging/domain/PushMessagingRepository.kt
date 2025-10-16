package com.example.push_messaging.domain

import com.example.push_messaging.domain.entity.CardPushMessageEntity

interface PushMessagingRepository {

    suspend fun insertCard(card : CardPushMessageEntity)
}