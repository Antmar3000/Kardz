package com.antmar.cards_list.domain.usecases

import android.util.Log
import com.antmar.core.domain.entity.CardId
import com.antmar.local_database.data.shared_data.SharedDataManager
import me.tatarka.inject.annotations.Inject

@Inject
class SendIdUseCase (
    private val sharedDataManager: SharedDataManager
) {
    suspend operator fun invoke(cardId: CardId) {
        sharedDataManager.updateSharedId(cardId.id)
    }
}