package com.antmar.card_scanner.domain.usecases

import android.util.Log
import com.antmar.local_database.data.shared_data.SharedDataManager
import kotlinx.coroutines.flow.SharedFlow
import me.tatarka.inject.annotations.Inject

@Inject
class GetSharedIdUseCase (
    private val sharedDataManager: SharedDataManager
) {

    operator fun invoke() : SharedFlow<Int>  {
        Log.d("myLog", "shared DM = ${sharedDataManager.toString()}")
        return sharedDataManager.editCardId
    }

    suspend fun clearEditCardId() = sharedDataManager.clearEditCardId()
}