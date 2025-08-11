package com.antmar.local_database.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardDBO (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val name : String,
    val code : String,
    val color : Long,
    val isBarcode : Boolean
)