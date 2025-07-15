package com.antmar.cards_list.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.antmar.cards_list.data.repository.CardsListRepositoryImpl
import com.antmar.cards_list.domain.CardsListRepository
import com.antmar.cards_list.domain.usecases.DeleteCardUseCase
import com.antmar.cards_list.domain.usecases.GetAllCardsUseCase
import com.antmar.cards_list.presentation.viewmodels.CardsListViewModel
import com.antmar.local_database.data.database.DAOs.CardsListDao
import com.antmar.local_database.di.DatabaseComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@Scope
@Retention
annotation class CardsListScope

@CardsListScope
@Component
abstract class CardsListComponent (
    @Component val databaseComponent: DatabaseComponent
) {

    @CardsListScope
    @Provides
    fun provideRepository ( impl: CardsListRepositoryImpl) : CardsListRepository = impl

    abstract fun getGetAllCardsUseCase() : GetAllCardsUseCase
    abstract fun getDeleteCardUseCase() : DeleteCardUseCase

    abstract fun cardsListViewModelFactory() : () -> CardsListViewModel
}




