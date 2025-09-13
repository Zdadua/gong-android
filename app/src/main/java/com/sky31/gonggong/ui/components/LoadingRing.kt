package com.sky31.gonggong.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sky31.gonggong.R
import com.sky31.gonggong.model.state.AnimationState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoadingRing(
    size: Dp = 20.dp,
    strokeWidth: Dp = 3.dp,
    color: Color = MaterialTheme.colorScheme.onBackground,
    state: AnimationState = AnimationState.Unstarted
) {
    val startAngle = remember { Animatable(0f) }
    val sweepAngle = remember { Animatable(240f) }

    LaunchedEffect(state) {
        if (state == AnimationState.Finished) {
            while (true) {
                coroutineScope {
                    launch {
                        startAngle.animateTo(
                            targetValue = 360f,
                            animationSpec = tween(
                                durationMillis = 1000,
                                easing = CubicBezierEasing(0.25f, 0.1f, 0.75f, 0.9f)
                            )
                        )
                        startAngle.snapTo(0f)
                    }

                    launch {
                        sweepAngle.animateTo(
                            targetValue = 270f,
                            animationSpec = tween(
                                durationMillis = 400,
                                easing = CubicBezierEasing(0.25f, 0.1f, 0.75f, 0.9f)
                            )
                        )

                        sweepAngle.animateTo(
                            targetValue = 120f,
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = CubicBezierEasing(0.25f, 0.1f, 0.75f, 0.9f)
                            )
                        )
                    }
                }
            }
        }
    }

    if (state !== AnimationState.Loading) {
        Icon(
            painter = painterResource(R.drawable.baseline_refresh_24),
            contentDescription = "refresh",
            tint = color,
            modifier = Modifier
                .size(size)
        )
    } else {
        Canvas(
            modifier = Modifier
                .size(size)
        ) {
            val diameter = size.toPx()
            val stroke = strokeWidth.toPx()

            drawArc(
                color = color,
                startAngle = startAngle.value,
                sweepAngle = sweepAngle.value,
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round),
                size = Size(diameter, diameter)
            )
        }
    }
}