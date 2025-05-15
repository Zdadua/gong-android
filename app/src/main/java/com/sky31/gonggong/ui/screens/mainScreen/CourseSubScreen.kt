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
import androidx.compose.runtime.LaunchedEffect
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
import com.sky31.gonggong.ui.DataState
import com.sky31.gonggong.ui.theme.LocalThemeColor
import com.sky31.gonggong.utils.TimeUtil
import com.sky31.gonggong.viewmodel.MainViewModel

/**
 * mainScreen今日课程子页面
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CourseSubScreen(viewModel: MainViewModel) {

    val courseList by remember { derivedStateOf { viewModel.courseList } }
    val currentTime by viewModel.currentTime
    val courseListState by viewModel.courseListState
    val backgroundColor = LocalThemeColor.current.backgroundColor

    // 用于改变圆形进度条的进度
    LaunchedEffect(courseList, currentTime) {
        // 防止进度条在未初始化和未加载时就开始动画
        if (courseListState == DataState.Loading || courseListState == DataState.Uninitialized) {
            return@LaunchedEffect
        }
        if (courseList.isEmpty()) {
            viewModel.setProgress(1f)
            return@LaunchedEffect
        }

        var accomplishment = 0f
        courseList.forEach {
            if (TimeUtil.getCourseState(
                    currentTime,
                    it.startTime,
                    it.duration
                ) is TimeUtil.CourseState.Before
            )
                accomplishment += 1f
        }
        viewModel.setProgress(accomplishment / courseList.size)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(15.dp))
            .padding(start = 5.dp, end = 5.dp)
    ) {
        // TODO 优化无课状态的UI
        if (courseList.isEmpty()) {
            Text(
                text = "今日无课",
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

                items(courseList) { course ->
                    CourseBox(course, currentTime)
                }
            }
        }

        // 表格上下模糊处理
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