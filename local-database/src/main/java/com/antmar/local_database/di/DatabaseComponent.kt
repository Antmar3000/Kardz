package com.antmar.local_database.di

import android.content.Context
import androidx.room.Room
import com.antmar.local_database.data.database.CardsDatabase
import com.antmar.local_database.data.shared_data.SharedDataManager
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope


@Scope
@Retention
annotation class DatabaseScope

@DatabaseScope
@Component
abstract class DatabaseComponent (
    private val context: Context
)  {

    private val database : CardsDatabase by lazy {
        Room.databaseBuilder(
            context,
            CardsDatabase::class.java,
            "cards_database"
        ).build()
    }

    @DatabaseScope
    @Provides
    fun provideDatabase() : CardsDatabase = database

    @DatabaseScope
    @Provides
    fun provideCardsListDao () = provideDatabase().getCardsDao()

    @DatabaseScope
    @Provides
    fun provideInsertDao (db : CardsDatabase) = db.getInsertDao()

    @DatabaseScope
    @Provides
    fun provideEditDao (db : CardsDatabase) = db.getEditDao()

    val sharedDataManager : SharedDataManager by lazy {
        SharedDataManager()
    }

    @DatabaseScope
    @Provides
    fun provideSharedDataManager() : SharedDataManager = sharedDataManager


}

