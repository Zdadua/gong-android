package com.sky31.gonggong.ui.screens.mainScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky31.gonggong.ui.theme.LocalThemeColor
import com.sky31.gonggong.ui.theme.Orange01

/**
 * 圆形进度条
 *
 * @param target 目标进度，取值范围为0-1
 * @param courseCount 课程数量
 */
@Composable
fun CircleProgressBar(target: Float, courseCount: Int) {
    val progress = remember { Animatable(0f) }
    val localThemeColor = LocalThemeColor.current

    LaunchedEffect(target) {
        if (target != -1f) {
            progress.animateTo(
                targetValue = target * 360f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = FastOutLinearInEasing
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = courseCount.toString(),
            fontWeight = FontWeight(700),
            fontSize = 24.sp,
            color = LocalThemeColor.current.textPrimary
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawArc(
                color = Orange01,
                startAngle = -90f,
                sweepAngle = progress.value,
                useCenter = false,
                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
            )

            drawArc(
                color = localThemeColor.boxColorPrimary,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}