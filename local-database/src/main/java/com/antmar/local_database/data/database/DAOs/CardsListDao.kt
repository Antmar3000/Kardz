package com.antmar.local_database.data.database.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.antmar.local_database.data.entity.CardDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsListDao {

    @Query("select * from cards")
    fun getAllCards () : Flow<List<CardDBO>>

    @Query("delete from cards where id = :id")
    suspend fun deleteCard (id : Int)

}