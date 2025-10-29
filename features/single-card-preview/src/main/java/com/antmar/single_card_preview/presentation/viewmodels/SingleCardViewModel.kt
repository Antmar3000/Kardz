package com.antmar.single_card_preview.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.single_card_preview.domain.usecases.DeleteCardUseCase
import com.antmar.single_card_preview.domain.usecases.SendCardUseCase
import com.antmar.single_card_preview.domain.usecases.GetCardUseCase
import com.antmar.single_card_preview.domain.usecases.GetSharedIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class SingleCardViewModel(
    private val deleteCardUseCase: DeleteCardUseCase,
    private val sendCardUseCase: SendCardUseCase,
    private val getCardUseCase: GetCardUseCase,
    private val getSharedIdUseCase: GetSharedIdUseCase
) : ViewModel() {

    private val _currentCardState = MutableStateFlow<CardUIEntity?>(null)
    val currentCardState = _currentCardState.asStateFlow()

    private val _dialogState = MutableStateFlow(false)
    val dialogState = _dialogState.asStateFlow()

    fun collectId() {
        viewModelScope.launch {
            getSharedIdUseCase.invoke().collect { id ->
                getCardUseCase(id).collect { cardUIEntity ->
                    _currentCardState.value = cardUIEntity
                }
            }
        }
    }

    fun toggleDeleteDialog() {
        _dialogState.value = !dialogState.value
    }

    fun deleteCard(id: Int) {
        viewModelScope.launch {
            deleteCardUseCase(id)
        }
    }

    fun sendCardId(id: Int) {
        viewModelScope.launch {
            sendCardUseCase(id)
        }
    }
}