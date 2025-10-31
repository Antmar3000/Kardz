package com.antmar.core.domain.entity

data class CardUIEntity (
    val id : Int,
    val name : String,
    val code : String,
    val color : Long,
    val isBarcode : Boolean
)

@JvmInline
value class CardId (val id : Int)