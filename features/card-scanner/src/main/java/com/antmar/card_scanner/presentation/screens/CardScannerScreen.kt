package com.antmar.card_scanner.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.antmar.card_scanner.di.CardScannerComponent
import com.antmar.card_scanner.di.create
import com.antmar.core.di.KIViewModel
import com.antmar.core.navigation.NavRoutes
import com.antmar.core.navigation.Navigator
import com.antmar.core.ui.ColorPalette
import com.antmar.local_database.di.DatabaseComponent
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.log10

@Composable
fun CardScannerScreen(databaseComponent: DatabaseComponent, navigator: Navigator) {

    val component = CardScannerComponent::class.create(databaseComponent)
    val viewModel = KIViewModel(component.cardScannerViewModelFactory())

    var inputStateName by remember { mutableStateOf("") }
    var inputStateCode by remember { mutableStateOf("") }

    val palette = ColorPalette.Default36
    var selectedColor by remember { mutableLongStateOf(0xFF808080) }

    val context = LocalContext.current

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

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = inputStateName,
                onValueChange = { inputStateName = it },
                placeholder = {
                    Text("введите название карты")
                },
                singleLine = true
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = inputStateCode,
                onValueChange = { newValue ->
                    if (newValue.all(Char::isDigit)) {
                        inputStateCode = if (newValue.isNotEmpty()) {
                            if (newValue.toLong() != 0L) newValue else ""
                        } else ""
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                placeholder = {
                    Text("введите штрих-код")
                }
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Scan")
                }

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
                    if (inputStateCode.length == 12 || inputStateCode.length == 13) {
                        viewModel.insertCard(
                            name = inputStateName,
                            code = inputStateCode.toLong(),
                            color = selectedColor
                        )
                        navigator.navigate(NavRoutes.LIST)
                    } else {
                        Toast.makeText(
                            context,
                            "number should contain 12 or 13 digits",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("добавить карту")
            }
        }
    }
}