package com.antmar.card_scanner.presentation.screens

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.antmar.card_scanner.di.CardScannerComponent
import com.antmar.card_scanner.di.create
import com.antmar.card_scanner.presentation.utils.ColorPickerDropdownMenu
import com.antmar.card_scanner.presentation.utils.RoundedTextField
import com.antmar.core.di.KIViewModel
import com.antmar.core.navigation.NavRoutes
import com.antmar.core.navigation.Navigator
import com.antmar.core.ui.ColorPalette
import com.antmar.local_database.di.DatabaseComponent

@Composable
fun CardAdditionScreen(databaseComponent: DatabaseComponent, navigator: Navigator) {

    val component = CardScannerComponent::class.create(databaseComponent)
    val viewModel = KIViewModel(component.cardScannerViewModelFactory())

    val inputStateName = remember { mutableStateOf("") }
    val inputStateCode = remember { mutableStateOf("") }
    var inputStateIsBarcode by remember { mutableStateOf(true) }

    val palette = ColorPalette.Default36
    var selectedColor by remember { mutableLongStateOf(ColorPalette.Default36.random()) }

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
                        inputStateCode.value = ""},
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
                    if (inputStateCode.value.isNotEmpty()) {
                        if (inputStateIsBarcode) {
                            if (inputStateCode.value.length == 12 || inputStateCode.value.length == 13) {
                                viewModel.insertCard(
                                    name = inputStateName.value,
                                    code = inputStateCode.value,
                                    color = selectedColor,
                                    isBarcode = inputStateIsBarcode
                                )
                                navigator.navigate(NavRoutes.LIST)
                            } else {
                                Toast.makeText(
                                    context,
                                    "barcode number should contain 12 or 13 digits",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            viewModel.insertCard(
                                name = inputStateName.value,
                                code = inputStateCode.value,
                                color = selectedColor,
                                isBarcode = inputStateIsBarcode
                            )
                            navigator.navigate(NavRoutes.LIST)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "code should not be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = Color.DarkGray
                )
            ) {
                Text("add card")
            }
        }
    }
}