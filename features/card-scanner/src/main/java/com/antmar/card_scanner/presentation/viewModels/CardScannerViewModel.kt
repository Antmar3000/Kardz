package com.antmar.card_scanner.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antmar.card_scanner.domain.usecases.GetCardUseCase
import com.antmar.card_scanner.domain.usecases.GetSharedIdUseCase
import com.antmar.card_scanner.domain.usecases.InsertCardUseCase
import com.antmar.card_scanner.domain.usecases.UpdateCardUseCase
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.core.utils.InputCheckedValue
import com.antmar.core.utils.checkInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class CardScannerViewModel(
    private val insertUseCase: InsertCardUseCase,
    private val getCardUseCase: GetCardUseCase,
    private val getSharedCardUseCase: GetSharedIdUseCase,
    private val updateCardUseCase: UpdateCardUseCase
) : ViewModel() {

    private val _currentCardState = MutableStateFlow<CardUIEntity?>(null)
    val currentCardState = _currentCardState.asStateFlow()

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

    fun collectCard() {
        viewModelScope.launch {
            getSharedCardUseCase.invoke().collect { id ->
                getCardUseCase(id).collect { cardUIEntity ->
                    _currentCardState.value = cardUIEntity
                }
            }
        }
    }

    fun clearCurrentCard() {
        viewModelScope.launch {
            _currentCardState.value = null
        }
    }

    override fun onCleared() {
        super.onCleared()
        clearCurrentCard()
    }

    fun updateOrInsertCard(
        id: Int?,
        name: String,
        code: String,
        color: Long,
        isBarcode: Boolean,
        onValidCode: () -> Unit,
        onInvalidCode: () -> Unit,
        onEmptyCode: () -> Unit
    ) {
       if (id != null) {

           when ( checkInput(name, isBarcode, code.length) ) {

               InputCheckedValue.VALID_CODE -> {
                   updateCard(id, name, code, color, isBarcode)
                   clearCurrentCard()
                   onValidCode()
               }
               InputCheckedValue.INVALID_CODE -> {
                   onInvalidCode()
               }
               InputCheckedValue.EMPTY_CODE -> {
                   onEmptyCode()
               }
           }
       }
        else {
           when ( checkInput(name, isBarcode, code.length) ) {

               InputCheckedValue.VALID_CODE -> {
                   insertCard(name, code, color, isBarcode)
                   clearCurrentCard()
                   onValidCode()
               }
               InputCheckedValue.INVALID_CODE -> {
                   onInvalidCode()
               }
               InputCheckedValue.EMPTY_CODE -> {
                   onEmptyCode()
               }
           }
       }
    }
}