package com.antmar.local_database.data.database.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.antmar.local_database.data.entity.CardDBO

@Dao
interface InsertCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard (card : CardDBO) : Long

}