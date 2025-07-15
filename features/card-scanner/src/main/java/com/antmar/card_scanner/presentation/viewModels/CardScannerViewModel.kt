package com.antmar.card_scanner.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antmar.card_scanner.domain.usecases.InsertCardUseCase
import com.antmar.core.domain.entity.CardUIEntity
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class CardScannerViewModel (
    private val insertUseCase : InsertCardUseCase
) : ViewModel() {

    init {

        val card = CardUIEntity(id = 0, name = "Card", code = 250L, color = "blue")

        viewModelScope.launch {
            insertUseCase(card)
        }
    }
}