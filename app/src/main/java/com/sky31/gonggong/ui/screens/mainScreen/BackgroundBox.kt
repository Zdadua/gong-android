package com.sky31.gonggong.ui.screens.mainScreen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp

/**
 * 动画背景
 */
@Composable
fun BackgroundBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    // infiniteRepeatable动画
    val transition = rememberInfiniteTransition(label = "cubic_bezier")
    val animatedControlPoint1 = transition.animateFloat(
        initialValue = .1f,
        targetValue = .4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "control_point1"
    )

    val animatedControlPoint2 = transition.animateFloat(
        initialValue = .4f,
        targetValue = .1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "control_point2"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(25.dp))
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .blur(
                    radiusX = 30.dp,
                    radiusY = 50.dp,
                )
        ) {
            val path = Path().apply {
                moveTo(50.dp.toPx(), size.height)

                // 动画控制贝塞尔曲线的控制点y坐标
                cubicTo(
                    size.width * 0.5f, size.height * animatedControlPoint1.value,
                    size.width * 0.8f, size.height * animatedControlPoint2.value,
                    size.width - 50.dp.toPx(), size.height * 1f
                )

                close()
            }

            drawPath(
                path = path,
                color = colorScheme.primary,
                style = Fill
            )
        }

        // 上层内容
        Box(
            modifier = Modifier
                .matchParentSize()
        ) {
            content()
        }
    }

}