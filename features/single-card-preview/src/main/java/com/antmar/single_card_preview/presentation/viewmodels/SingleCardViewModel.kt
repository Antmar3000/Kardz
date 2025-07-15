package com.antmar.single_card_preview.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.antmar.single_card_preview.domain.usecases.DeleteCardUseCase
import com.antmar.single_card_preview.domain.usecases.EditCardUseCase
import me.tatarka.inject.annotations.Inject

@Inject
class SingleCardViewModel (
    private val deleteCardUseCase: DeleteCardUseCase,
    private val editCardUseCase: EditCardUseCase
) : ViewModel() {

    init {
        Log.d("myLog", "$deleteCardUseCase + //// + $editCardUseCase")
    }

}