package com.antmar.kardz

import android.app.Application
import android.util.Log
import com.antmar.local_database.di.DatabaseComponent
import com.antmar.local_database.di.create
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.initialize

class App : Application() {

    val databaseComponent by lazy { DatabaseComponent::class.create(applicationContext) }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}