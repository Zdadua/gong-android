package com.sky31.gonggong.ui.screens.scoreScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.sky31.gonggong.ui.theme.LocalThemeColor
import com.sky31.gonggong.ui.theme.Orange01
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun AcademicBottomBar(pagerState: PagerState) {
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()
    var size by remember { mutableStateOf(IntSize.Zero) }
    var initialOffset by remember { mutableIntStateOf(0) }

    LaunchedEffect(size) {
        initialOffset = size.width / 4 - with(density) { 50.dp.toPx() }.toInt()
    }

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .onSizeChanged {
                size = it
            },
        containerColor = Color.Transparent,
        contentPadding = PaddingValues(0.dp)
    ) {
        Column {
            Row() {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            scope.launch { pagerState.animateScrollToPage(0) }
                        },
                    text = "学业总览",
                    textAlign = TextAlign.Center,
                    color = LocalThemeColor.current.textSecondary
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            scope.launch { pagerState.animateScrollToPage(1) }
                        },
                    text = "成绩表单",
                    textAlign = TextAlign.Center,
                    color = LocalThemeColor.current.textSecondary
                )
            }

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = ((pagerState.currentPage + pagerState.currentPageOffsetFraction) * size.width / 2).toInt() + initialOffset,
                            y = 0
                        )
                    }
                    .scale(
                        scaleX = 1 + abs(pagerState.currentPageOffsetFraction) * 1.5f,
                        scaleY = 1 - abs(pagerState.currentPageOffsetFraction) * 0.5f
                    )
                    .width(100.dp)
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Orange01)
            )
        }
    }
}