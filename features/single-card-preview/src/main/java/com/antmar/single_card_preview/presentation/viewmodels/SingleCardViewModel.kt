package com.antmar.single_card_preview.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.local_database.data.shared_data.SharedDataManager
import com.antmar.single_card_preview.domain.usecases.DeleteCardUseCase
import com.antmar.single_card_preview.domain.usecases.EditCardUseCase
import com.antmar.single_card_preview.domain.usecases.GetCardUseCase
import com.antmar.single_card_preview.domain.usecases.GetSharedIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class SingleCardViewModel(
    private val deleteCardUseCase: DeleteCardUseCase,
    private val editCardUseCase: EditCardUseCase,
    private val getCardUseCase: GetCardUseCase,
    private val getSharedIdUseCase : GetSharedIdUseCase
) : ViewModel() {

    init {
        collectId()
    }

    private val _currentCardState = MutableStateFlow<CardUIEntity?>(null)
    val currentCardState get() = _currentCardState.asStateFlow()

    private fun collectId() {
        viewModelScope.launch {
            getSharedIdUseCase.invoke().collect {id ->
                getCardUseCase(id).collect {cardUIEntity ->
                    _currentCardState.value = cardUIEntity
                }
            }
        }
    }
}