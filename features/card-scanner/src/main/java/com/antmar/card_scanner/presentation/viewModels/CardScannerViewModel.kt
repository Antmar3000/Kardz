package com.antmar.card_scanner.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antmar.card_scanner.domain.usecases.GetCardUseCase
import com.antmar.card_scanner.domain.usecases.GetSharedIdUseCase
import com.antmar.card_scanner.domain.usecases.InsertCardUseCase
import com.antmar.core.domain.entity.CardUIEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class CardScannerViewModel(
    private val insertUseCase: InsertCardUseCase,
    private val getCardUseCase: GetCardUseCase,
    private val getSharedCardUseCase: GetSharedIdUseCase,
) : ViewModel() {

    init {
        collectCard()
    }

    private val _currentCardState = MutableStateFlow<CardUIEntity?>(null)
    val currentCardState get() = _currentCardState.asStateFlow()

    fun insertCard(
        name: String,
        code: String,
        color: Long,
        isBarcode: Boolean
    ) {
        val card =
            CardUIEntity(id = 0, name = name, code = code, color = color, isBarcode = isBarcode)
        viewModelScope.launch {
            insertUseCase(card)
        }
    }

    private fun collectCard() {
        viewModelScope.launch {
            getSharedCardUseCase.invoke().collect { id ->
                getCardUseCase.invoke(id).collect { cardUIEntity ->
                    _currentCardState.update { it }
                }
            }
        }
    }
}