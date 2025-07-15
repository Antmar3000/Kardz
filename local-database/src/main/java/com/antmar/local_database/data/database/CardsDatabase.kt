package com.antmar.local_database.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.antmar.local_database.data.database.DAOs.CardsListDao
import com.antmar.local_database.data.database.DAOs.EditCardDao
import com.antmar.local_database.data.database.DAOs.InsertCardDao
import com.antmar.local_database.data.entity.CardDBO

@Database(entities = [CardDBO::class], version = 1)
abstract class CardsDatabase : RoomDatabase() {

    abstract fun getCardsDao() : CardsListDao

    abstract fun getInsertDao() : InsertCardDao

    abstract fun getEditDao() : EditCardDao
}