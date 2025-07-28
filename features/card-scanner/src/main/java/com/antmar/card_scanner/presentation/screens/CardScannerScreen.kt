package com.antmar.card_scanner.presentation.screens

import android.util.Log
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.antmar.card_scanner.di.CardScannerComponent
import com.antmar.card_scanner.di.create
import com.antmar.core.di.KIViewModel
import com.antmar.core.navigation.NavRoutes
import com.antmar.core.navigation.Navigator
import com.antmar.core.ui.ColorPalette
import com.antmar.local_database.di.DatabaseComponent

@Composable
fun CardScannerScreen(databaseComponent: DatabaseComponent, navigator: Navigator) {

    Log.d("myLog", databaseComponent.provideDatabase().toString())

    val component = CardScannerComponent::class.create(databaseComponent)
    val viewModel = KIViewModel(component.cardScannerViewModelFactory())
    Log.d("myLog", viewModel.toString())

    var inputStateName by remember { mutableStateOf("") }
    var inputStateCode by remember { mutableLongStateOf(0L) }

    val palette = ColorPalette.Default36
    var selectedColor by remember { mutableLongStateOf(0xFF808080) }

//    val vm = CardScannerComponent::class.create(databaseComponent).cardScannerViewModelFactory().injectViewModel()

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

            TextField(modifier = Modifier.fillMaxWidth(),
                value = inputStateCode.toString(),
                onValueChange = { newValue ->
                    if (newValue.all(Char::isDigit)) {
                        inputStateCode = newValue.toLong()
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
                    viewModel.insertCard(
                        name = inputStateName,
                        code = inputStateCode,
                        color = selectedColor
                    )
                    navigator.navigate(NavRoutes.LIST)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("добавить карту")
            }
        }
    }


}