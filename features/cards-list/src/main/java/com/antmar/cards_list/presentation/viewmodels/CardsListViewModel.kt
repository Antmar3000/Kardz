package com.antmar.cards_list.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antmar.cards_list.domain.usecases.DeleteCardUseCase
import com.antmar.cards_list.domain.usecases.GetAllCardsUseCase
import com.antmar.cards_list.domain.usecases.SendIdUseCase
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.local_database.data.shared_data.SharedDataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class CardsListViewModel(
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val deleteCardUseCase: DeleteCardUseCase,
    private val sendIdUseCase: SendIdUseCase
) : ViewModel() {

    private val _allCardsListState = MutableStateFlow<List<CardUIEntity>>(emptyList())
    val allCardsListState get() = _allCardsListState.asStateFlow()

    private val _dialogState = MutableStateFlow(-1)
    val dialogState get() = _dialogState.asStateFlow()

    init {
        collectCards()
    }

    private fun collectCards() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllCardsUseCase.invoke().collect {
                _allCardsListState.value = it
            }
        }
    }

    fun deleteCard(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(250)
            deleteCardUseCase(id)
        }
    }

    fun sendCardId(id: Int) {
        viewModelScope.launch {
            sendIdUseCase(id)
        }
    }

    fun toggleDeleteDialog(id: Int) {
        _dialogState.value = id
    }
}