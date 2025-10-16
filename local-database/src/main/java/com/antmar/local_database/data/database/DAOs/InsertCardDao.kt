package com.antmar.local_database.data.database.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.antmar.local_database.data.entity.CardDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface InsertCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard (card : CardDBO) : Long

    @Update
    suspend fun updateCard (card: CardDBO)

    @Query("select * from cards where id = :id")
    fun getCard(id : Int) : Flow<CardDBO?>

}