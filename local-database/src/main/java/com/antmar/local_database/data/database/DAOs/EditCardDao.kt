package com.antmar.local_database.data.database.DAOs

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.antmar.local_database.data.entity.CardDBO

@Dao
interface EditCardDao {

    @Query("delete from cards where id = :id")
    suspend fun deleteCard (id : Int)

    @Update
    suspend fun updateCard (card: CardDBO)
}