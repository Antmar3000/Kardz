package com.antmar.cards_list.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.core.navigation.Navigator
import java.nio.file.WatchEvent

@Composable
fun CardListItem(card: CardUIEntity, navigate : () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(8.dp)
            .clickable(onClick = navigate)
            .border(
                width = 2.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(card.color)
        ),
        shape = RoundedCornerShape(16.dp)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(card.name, fontSize = 40.sp)
        }
    }
}

val mockCard = CardUIEntity(
    1,
    "LENTA",
    14424323551L,
    0xFF808000
)
@Preview
@Composable
fun listitempreview () {
    CardListItem(mockCard, {})
}