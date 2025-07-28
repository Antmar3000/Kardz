package com.antmar.single_card_preview.di

import com.antmar.local_database.di.DatabaseComponent
import com.antmar.single_card_preview.data.repository.SingleCardRepositoryImpl
import com.antmar.single_card_preview.domain.SingleCardRepository
import com.antmar.single_card_preview.domain.usecases.DeleteCardUseCase
import com.antmar.single_card_preview.domain.usecases.EditCardUseCase
import com.antmar.single_card_preview.domain.usecases.GetCardUseCase
import com.antmar.single_card_preview.domain.usecases.GetSharedIdUseCase
import com.antmar.single_card_preview.presentation.viewmodels.SingleCardViewModel
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope


@Scope
@Retention
annotation class SingleCardScope

@SingleCardScope
@Component
abstract class SingleCardComponent(
    @Component val databaseComponent: DatabaseComponent
) {

    @SingleCardScope
    @Provides
    fun provideRepository (impl : SingleCardRepositoryImpl) : SingleCardRepository = impl

    abstract fun getDeleteCardUseCase () : DeleteCardUseCase
    abstract fun getEditCardUseCase () : EditCardUseCase
    abstract fun getGetCardUseCase() : GetCardUseCase
    abstract fun getGetSharedIdUseCase() : GetSharedIdUseCase

    abstract fun singleCardViewModelFactory() : () -> SingleCardViewModel
}