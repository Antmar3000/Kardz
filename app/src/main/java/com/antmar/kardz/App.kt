package com.antmar.kardz

import android.app.Application
import com.antmar.local_database.di.DatabaseComponent
import com.antmar.local_database.di.create

class App : Application() {

    val databaseComponent by lazy { DatabaseComponent::class.create(applicationContext) }
}