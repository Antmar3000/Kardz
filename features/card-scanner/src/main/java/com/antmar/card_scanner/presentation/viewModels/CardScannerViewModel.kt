package com.antmar.card_scanner.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antmar.card_scanner.domain.usecases.InsertCardUseCase
import com.antmar.core.domain.entity.CardUIEntity
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class CardScannerViewModel(
    private val insertUseCase: InsertCardUseCase
) : ViewModel() {

    fun insertCard(
        name: String,
        code: Long,
        color: Long
    ) {
        val card = CardUIEntity(id = 0, name = name, code = code, color = color)
        viewModelScope.launch {
            insertUseCase(card)
        }
    }
}