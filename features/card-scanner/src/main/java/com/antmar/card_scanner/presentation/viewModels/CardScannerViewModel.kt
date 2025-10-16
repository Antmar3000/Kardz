package com.antmar.card_scanner.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antmar.card_scanner.domain.usecases.GetCardUseCase
import com.antmar.card_scanner.domain.usecases.GetSharedIdUseCase
import com.antmar.card_scanner.domain.usecases.InsertCardUseCase
import com.antmar.card_scanner.domain.usecases.UpdateCardUseCase
import com.antmar.core.domain.entity.CardUIEntity
import kotlinx.coroutines.delay
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
    private val updateCardUseCase: UpdateCardUseCase
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
        viewModelScope.launch {
            insertUseCase(name, code, color, isBarcode)
        }
    }

    fun updateCard(
        id: Int,
        name: String,
        code: String,
        color: Long,
        isBarcode: Boolean) {
        viewModelScope.launch {
            updateCardUseCase(id, name, code, color, isBarcode)
        }
    }

    private fun collectCard() {
        viewModelScope.launch {
            getSharedCardUseCase.invoke().collect { id ->
                Log.d("myLog", "scannerVM, getSharedId = $id")
                getCardUseCase(id).collect { cardUIEntity ->
                    Log.d("myLog", "scannerVM, getSharedCard = $cardUIEntity")
                    _currentCardState.value = cardUIEntity
                }
            }
        }
    }

    fun clearEditCardId() {
        viewModelScope.launch {
            getSharedCardUseCase.clearEditCardId()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("myLog", "cleared VM")
        clearEditCardId()
    }
}