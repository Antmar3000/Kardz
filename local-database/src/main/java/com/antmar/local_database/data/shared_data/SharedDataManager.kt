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
    val sharedId get() = _sharedId.asSharedFlow()

    private val _editCardId = MutableSharedFlow<Int>(replay = 1)
    val editCardId get() = _editCardId.asSharedFlow()

    suspend fun updateSharedId(id: Int) {
        Log.d("myLog", "shared manager, id = $id")
        _sharedId.emit(id)
    }

    suspend fun updateEditCardId(id: Int) {
        Log.d("myLog", "shared manager, edit card, id = $id")
        _editCardId.emit(id)
    }

    suspend fun clearEditCardId() {
        _editCardId.emit(-1)
    }
}