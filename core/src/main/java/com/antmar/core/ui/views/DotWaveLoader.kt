package com.antmar.core.ui.views

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun DotWaveLoader(
    modifier: Modifier = Modifier,
    dotCount: Int = 20,
    dotSize: Dp = 8.dp,
    dotColor: Color = MaterialTheme.colorScheme.primary,
    animationDuration: Int = 2000,
    waveRadius: Dp = 40.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "dotWave")

    val scales = List(dotCount) { index ->
        val delay = (animationDuration / dotCount) * index
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(

//                animation = tween (
//                    durationMillis = animationDuration,
//                    easing = LinearOutSlowInEasing
//                ),

                animation = keyframes {
                    durationMillis = animationDuration
                    0.3f at 0
                    1f at (animationDuration / 2)
                    0.3f at animationDuration
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(delay)
            ),
            label = "dotScale_$index"
        )
    }

    val opacities = List(dotCount) { index ->
        val delay = (animationDuration / dotCount) * index
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration
                    0.3f at 0
                    1f at (animationDuration / 2)
                    0.3f at animationDuration
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(delay)
            ),
            label = "dotOpacity_$index"
        )
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(waveRadius * 2 + dotSize)) {

            // по кругу точки

//            val center = center
//            val radius = waveRadius.toPx()
//
//            val angleStep = 360f / dotCount
//
//            repeat(dotCount) { index ->
//                val angle = Math.toRadians((angleStep * index).toDouble())
//                val x = center.x + radius * cos(angle).toFloat()
//                val y = center.y + radius * sin(angle).toFloat()
//
//                val scale = scales[index].value
//                val opacity = scales[index].value
//
//                drawCircle(
//                    color = dotColor.copy(alpha = opacity),
//                    center = Offset(x, y),
//                    radius = (dotSize.toPx() / 2) * scale,
//                    alpha = opacity
//                )
//            }

            // по квадрату точки


            val sideLength = waveRadius.toPx()
            val centerX = (size.width - sideLength) / 2
            val centerY = (size.height - sideLength) / 2

            val dotsPerSide = dotCount / 4
            val extraDots = dotCount % 4

            var currentDot = 0

            fun drawSidePoints(
                startX: Float, startY: Float,
                endX: Float, endY: Float,
                dotCountOnSide: Int
            ) {
                repeat(dotCountOnSide) { sideIndex ->
                    if (currentDot < dotCount) {
                        val progress = sideIndex.toFloat() / (dotCountOnSide - 1).coerceAtLeast(1)
                        val x = startX + (endX - startX) * progress
                        val y = startY + (endY - startY) * progress

                        val scale = scales[currentDot].value
                        val opacity = 0.3f + scale * 0.7f

                        drawCircle(
                            color = dotColor.copy(alpha = opacity),
                            center = Offset(x, y),
                            radius = (dotSize.toPx() / 2) * scale
                        )

                        currentDot++
                    }
                }
            }

            drawSidePoints(
                startX = centerX,
                startY = centerY,
                endX = centerX + sideLength,
                endY = centerY,
                dotCountOnSide = dotsPerSide + if (extraDots > 0) 1 else 0
            )

            drawSidePoints(
                startX = centerX + sideLength,
                startY = centerY,
                endX = centerX + sideLength,
                endY = centerY + sideLength,
                dotCountOnSide = dotsPerSide + if (extraDots > 1) 1 else 0
            )

            drawSidePoints(
                startX = centerX + sideLength,
                startY = centerY + sideLength,
                endX = centerX,
                endY = centerY + sideLength,
                dotCountOnSide = dotsPerSide + if (extraDots > 2) 1 else 0
            )

            drawSidePoints(
                startX = centerX,
                startY = centerY + sideLength,
                endX = centerX,
                endY = centerY,
                dotCountOnSide = dotsPerSide
            )
        }
    }
}
