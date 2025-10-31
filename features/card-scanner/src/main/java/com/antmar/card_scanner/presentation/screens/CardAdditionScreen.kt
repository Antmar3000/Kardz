package com.antmar.card_scanner.presentation.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antmar.card_scanner.di.CardScannerComponent
import com.antmar.card_scanner.di.create
import com.antmar.card_scanner.presentation.utils.ColorPickerDropdownMenu
import com.antmar.card_scanner.presentation.utils.RoundedTextField
import com.antmar.core.di.KIViewModel
import com.antmar.core.navigation.NavRoutes
import com.antmar.core.navigation.Navigator
import com.antmar.core.ui.ColorPalette
import com.antmar.core.utils.InputCheckedValue
import com.antmar.core.utils.checkInput
import com.antmar.local_database.di.DatabaseComponent

@Composable
fun CardAdditionScreen(
    databaseComponent: DatabaseComponent,
    navigator: Navigator,
    prefillName: String? = null,
    prefillCode: String? = null,
    prefillIsBarcode: Boolean? = null
) {

    val component = CardScannerComponent::class.create(databaseComponent)
    val viewModel = KIViewModel(component.cardScannerViewModelFactory())

    val currentCard = viewModel.currentCardState.collectAsStateWithLifecycle().value

    val inputStateName = remember { mutableStateOf("") }
    val inputStateCode = remember { mutableStateOf("") }
    var inputStateIsBarcode by remember { mutableStateOf(true) }

    val palette = ColorPalette.Default36
    var selectedColor by remember { mutableLongStateOf(ColorPalette.Default36.random()) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.collectCard()
    }

    LaunchedEffect(prefillName, prefillCode, prefillIsBarcode) {
        Log.d("DeepLinkDebug", "🔍 Received parameters:")
        Log.d("DeepLinkDebug", "   name: '$prefillName'")
        Log.d("DeepLinkDebug", "   code: '$prefillCode'")
        Log.d("DeepLinkDebug", "   isBarcode: $prefillIsBarcode")
    }

    LaunchedEffect(currentCard) {
        if (currentCard != null) {
            inputStateName.value = currentCard.name
            inputStateCode.value = currentCard.code
            inputStateIsBarcode = currentCard.isBarcode
            selectedColor = currentCard.color
        } else {
            prefillName?.let { inputStateName.value = it }
            prefillCode?.let { inputStateCode.value = it }
            prefillIsBarcode?.let { inputStateIsBarcode = it }
        }
    }

    BackHandler(enabled = true) {
        viewModel.clearCurrentCard()
        navigator.popBackStack()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            RoundedTextField("input name", inputStateName, false)

            RoundedTextField("input card number", inputStateCode, inputStateIsBarcode)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Switch(
                    checked = inputStateIsBarcode,
                    onCheckedChange = {
                        inputStateIsBarcode = it
                        inputStateCode.value = ""
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = MaterialTheme.colorScheme.onBackground,
                        uncheckedThumbColor = MaterialTheme.colorScheme.surface
                    )
                )

                Text(
                    text = if (inputStateIsBarcode) "barcode" else "QR-code",
                    color = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                )

                ColorPickerDropdownMenu(
                    colors = palette,
                    selectedColor = selectedColor,
                    modifier = Modifier.weight(1f)
                ) {
                    selectedColor = it
                }
            }

            Button(
                onClick = {
                    viewModel.updateOrInsertCard(
                        currentCard?.id,
                        inputStateName.value,
                        inputStateCode.value,
                        selectedColor,
                        inputStateIsBarcode,
                        {
                            navigator.popBackStack()
                        },
                        {
                            makeInvalidCodeLengthText(context)
                        },
                        {
                            makeEmptyCodeText(context)
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = Color.DarkGray
                )
            ) {
                Text(if (currentCard != null) "update card" else "add card")
            }
        }
    }
}

fun makeInvalidCodeLengthText(context: Context) {
    Toast.makeText(context, "code should be 12 or 13 digits long", Toast.LENGTH_SHORT).show()
}

fun makeEmptyCodeText (context: Context) {
    Toast.makeText(context, "code should not be empty", Toast.LENGTH_SHORT).show()
}

