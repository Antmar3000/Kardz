package com.antmar.cards_list.domain.usecases

import android.util.Log
import com.antmar.local_database.data.shared_data.SharedDataManager
import me.tatarka.inject.annotations.Inject

@Inject
class SendIdUseCase (
    private val sharedDataManager: SharedDataManager
) {
    suspend operator fun invoke(id : Int) {
        sharedDataManager.updateSharedId(id)
    }
}