package com.gpn.azs.add_virtual_card.release_pager.compose

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.antmar.cards_list.presentation.viewmodels.CardsListViewModel
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun VirtualCardReleasedScreen(
    onNextClick: (Int) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })

    Scaffold(
        contentColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            HorizontalPager(modifier = Modifier.weight(1f),
                state = pagerState
            ) { page ->
                when (page) {
                    0 -> VirtualCardReleasedPage(
                        imageRes = R.drawable.star_on,
                        text = "text1"
                    )

                    1 -> VirtualCardReleasedPage(
                        imageRes = R.drawable.star_off,
                        text = "text2"
                    )
                }
            }


            DotsIndicator(
                modifier = Modifier
                    .padding(16.dp),
                totalDots = pagerState.pageCount,
                selectedIndex = pagerState.currentPage
            )

            Spacer(Modifier.height(24.dp))

            val buttonText: String =
                if (pagerState.currentPage == 0) "R.string.action_next"
                else "R.string.add_card_virtual_done_btn_text"

            Button(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    disabledContainerColor = Color.Cyan,
                    contentColor = Color.White,
                    disabledContentColor = Color.Red
                ),
                onClick = { onNextClick(pagerState.currentPage) }
            ) {
                Text(text = buttonText, fontSize = 30.sp)
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}


@Composable
fun VirtualCardReleasedPage(
    imageRes: Int,
    text: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.wrapContentSize(),
            painter = painterResource(imageRes),
            contentDescription = null
        )
        Spacer(Modifier.height(32.dp))
        Text(
            fontSize = 20.sp,
            text = text,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}


@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalDots) { index ->
            val color = if (index == selectedIndex) Color.Red else Color.Blue
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Preview
@Composable
fun ButtonPreview() {
    VirtualCardReleasedScreen { }
}

