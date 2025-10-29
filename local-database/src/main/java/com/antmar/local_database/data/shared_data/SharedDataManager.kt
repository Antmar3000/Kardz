package com.antmar.local_database.data.shared_data

import android.util.Log
import com.antmar.local_database.data.entity.CardDBO
import com.antmar.local_database.data.entity.EditCard
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import me.tatarka.inject.annotations.Inject

@Inject
class SharedDataManager {

    private val _sharedId = MutableSharedFlow<Int>(replay = 1)
    val sharedId = _sharedId.asSharedFlow()

    suspend fun updateSharedId(id: Int) {
        _sharedId.emit(id)
    }
}