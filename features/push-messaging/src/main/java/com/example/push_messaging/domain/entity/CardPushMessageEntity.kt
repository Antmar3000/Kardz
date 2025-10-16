package com.example.push_messaging.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class CardPushMessageEntity(
    val name: String,
    val code: String,
    val color: Long,
    val isBarcode: Boolean
)
