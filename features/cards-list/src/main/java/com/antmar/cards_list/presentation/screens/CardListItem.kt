package com.antmar.cards_list.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antmar.core.domain.entity.CardUIEntity
import com.antmar.core.navigation.Navigator
import java.nio.file.WatchEvent
import kotlin.math.absoluteValue

@Composable
fun CardListItem(card: CardUIEntity, navigate: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable(onClick = navigate)
            .padding(8.dp)
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

@Composable
fun SwipeToDeleteCardListItem(
    card: CardUIEntity,
    navigate: () -> Unit,
    onSwipe: (Int) -> Unit
) {

    val threshold = LocalConfiguration.current.screenWidthDp.dp.value * 0.8f

    val swipeState = rememberSwipeToDismissBoxState(
        confirmValueChange = { newValue ->
            if (newValue == SwipeToDismissBoxValue.EndToStart) {
                onSwipe(card.id)
                true
            } else {
                false
            }
        },
        positionalThreshold = { threshold }
    )

    LaunchedEffect(swipeState.currentValue) {
        if (swipeState.currentValue == SwipeToDismissBoxValue.EndToStart) {
            swipeState.reset()
        }
    }

    SwipeToDismissBox(
        state = swipeState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {

            val alpha = (swipeState.progress * 1.5f).coerceAtMost(1f)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = alpha),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red.copy(alpha = alpha))
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete",
                        tint = Color.White,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }
        }) {
        CardListItem(card, navigate)
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
fun listitempreview() {
    CardListItem(mockCard, {})
}