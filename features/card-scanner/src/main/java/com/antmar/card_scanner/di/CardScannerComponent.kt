package com.antmar.card_scanner.di

import com.antmar.card_scanner.data.repository.CardScannerRepositoryImpl
import com.antmar.card_scanner.domain.CardScannerRepository
import com.antmar.card_scanner.domain.usecases.InsertCardUseCase
import com.antmar.card_scanner.presentation.viewModels.CardScannerViewModel
import com.antmar.local_database.di.DatabaseComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope


@Scope
@Retention
annotation class CardScannerScope

@CardScannerScope
@Component
abstract class CardScannerComponent(
    @Component val databaseComponent: DatabaseComponent
) {

    @CardScannerScope
    @Provides
    fun provideRepository (impl : CardScannerRepositoryImpl) : CardScannerRepository = impl

    abstract fun getInsertCardUseCase () : InsertCardUseCase

    abstract fun cardScannerViewModelFactory() : () -> CardScannerViewModel
}