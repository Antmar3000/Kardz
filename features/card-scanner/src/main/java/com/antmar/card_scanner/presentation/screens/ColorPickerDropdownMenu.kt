package com.antmar.card_scanner.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.antmar.core.ui.getColorBasedOnBackground

@Composable
fun ColorPickerDropdownMenu(
    colors: List<Long>,
    selectedColor: Long?,
    modifier: Modifier,
    onColorSelected: (Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(), onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(selectedColor ?: 0xFFFFFFFF)
            )
        ) {
            Text(
                text = "Select color",
                color = getColorBasedOnBackground(selectedColor ?: 0xFFFFFFFF)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(6),
                modifier = Modifier
                    .size(240.dp)
                    .padding(8.dp)
            ) {
                items(colors) { colorLong ->
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .padding(4.dp)
                            .background(Color(colorLong))
                            .border(
                                width = if (colorLong == selectedColor) 2.dp else 1.dp,
                                color = if (colorLong == selectedColor) Color.Black else Color.Gray,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .clickable {
                                onColorSelected(colorLong)
                                expanded = false
                            }
                    )
                }
            }
        }
    }
}