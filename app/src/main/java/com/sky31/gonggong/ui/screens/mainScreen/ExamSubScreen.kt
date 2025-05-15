package com.sky31.gonggong.ui.screens.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky31.gonggong.ui.theme.LocalThemeColor
import com.sky31.gonggong.viewmodel.MainViewModel

/**
 * mainScreen的考试列表子页面
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExamSubScreen(viewModel: MainViewModel) {

    val examList by remember { derivedStateOf { viewModel.examList } }
    val currentTime by viewModel.currentTime
    val backgroundColor = LocalThemeColor.current.backgroundColor

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(15.dp))
            .padding(start = 5.dp, end = 5.dp)
    ) {
        // TODO 优化无考试状态的UI
        if (examList.isEmpty()) {
            Text(
                text = "暂无考试安排",
                color = LocalThemeColor.current.textSecondary,
                fontSize = 24.sp,
                fontWeight = FontWeight(700),
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
            ) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(examList) { exam ->
                    ExamBox(exam, currentTime)
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        // 上下模糊
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawRect(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to backgroundColor,     // 顶部 0%
                        0.05f to Color.Transparent,
                        0.95f to Color.Transparent,  // 中间 50%
                        1.0f to backgroundColor    // 底部 100%
                    )
                ),
                size = size
            )
        }
    }
}