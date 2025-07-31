package com.antmar.cards_list.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antmar.cards_list.domain.usecases.DeleteCardUseCase
import com.antmar.cards_list.domain.usecases.GetAllCardsUseCase
import com.antmar.cards_list.domain.usecases.SendIdUseCase
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.local_database.data.shared_data.SharedDataManager
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

    init {
        collectCards()
    }

    private fun collectCards() {
        viewModelScope.launch {
            getAllCardsUseCase.invoke().collect {
                _allCardsListState.value = it
            }
        }
    }

    fun deleteCard(id : Int) {
        viewModelScope.launch {
            deleteCardUseCase(id)
        }
    }

    fun sendCardId (id: Int) {
        viewModelScope.launch {
            sendIdUseCase(id)
        }
    }
}