package com.antmar.cards_list.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antmar.cards_list.domain.usecases.DeleteCardUseCase
import com.antmar.cards_list.domain.usecases.GetAllCardsUseCase
import com.antmar.core.domain.entity.CardUIEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.compose
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class CardsListViewModel(
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val deleteCardUseCase: DeleteCardUseCase
) : ViewModel() {

    private val getAllCardsState = MutableStateFlow<List<CardUIEntity>>(emptyList())

    init {
        collectCards()
    }

    private fun collectCards() {
        viewModelScope.launch {
            getAllCardsUseCase.invoke().collect {
                delay(500)
                getAllCardsState.value = it
                Log.d("myLog", "delayed size = ${getAllCardsState.value.size}")
            }
        }
    }

}