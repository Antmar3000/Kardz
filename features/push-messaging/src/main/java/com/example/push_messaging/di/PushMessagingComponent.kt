package com.example.push_messaging.di

import com.antmar.local_database.di.DatabaseComponent
import com.example.push_messaging.data.repository.PushMessagingRepositoryImpl
import com.example.push_messaging.domain.PushMessagingRepository
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope


@Scope
@Retention
annotation class PushMessagingScope


@PushMessagingScope
@Component
abstract class PushMessagingComponent (
    @Component val databaseComponent: DatabaseComponent
) {

    @PushMessagingScope
    @Provides
    fun provideRepository (impl : PushMessagingRepositoryImpl) : PushMessagingRepository = impl
}