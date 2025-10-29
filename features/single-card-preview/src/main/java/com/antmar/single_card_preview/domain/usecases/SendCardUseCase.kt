package com.antmar.single_card_preview.domain.usecases

import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.local_database.data.shared_data.SharedDataManager
import com.antmar.single_card_preview.data.mappers.toDBO
import com.antmar.single_card_preview.domain.SingleCardRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SendCardUseCase(
    private val sharedDataManager: SharedDataManager
) {
    suspend operator fun invoke(id: Int) = sharedDataManager.updateSharedId(id)
}