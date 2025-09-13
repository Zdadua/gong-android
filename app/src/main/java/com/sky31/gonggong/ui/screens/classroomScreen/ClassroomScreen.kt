package com.sky31.gonggong.ui.screens.classroomScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sky31.gonggong.viewmodel.ClassroomViewModel

// 课程时间段字符串
val periodStrList = listOf("1-2", "3-4", "5-6", "7-8", "9-11")

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClassroomScreen() {
    val viewModel: ClassroomViewModel = hiltViewModel()

    // 地点列表的左右阴影颜色
    val shadowColor = MaterialTheme.colorScheme.background

    val todayClassroomMap = viewModel.todayClassroomMap
    val todayDate by viewModel.todayDate
    val tomorrowClassroomMap = viewModel.tomorrowClassroomMap
    val tomorrowDate by viewModel.tomorrowDate
    val locationList = remember { derivedStateOf { todayClassroomMap.keys.toList() } }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 2 }
    )
    val currentLocation = remember { mutableStateOf<String?>(null) }
    val currentDate =
        remember { derivedStateOf { if (pagerState.currentPage == 0) todayDate else tomorrowDate } }

    // 选中时间段，根据该数组筛选空教室
    val periodStatus = remember { mutableStateListOf(false, false, false, false, false) }

    LaunchedEffect(Unit) {
        viewModel.updateData()

        println(todayDate.toString())
    }

    LaunchedEffect(locationList.value) {
        currentLocation.value = locationList.value.firstOrNull()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 时间信息
        Row(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 5.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            if (currentDate.value != null) {
                Text(
                    text = "${currentDate.value!!.monthValue}月${currentDate.value!!.dayOfMonth}日",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                    fontWeight = FontWeight(800)
                )

                Text(
                    modifier = Modifier
                        .padding(start = 10.dp),
                    text = "${if (pagerState.currentPage == 0) "今" else "明"}天",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(800)
                )
            }
        }

        // 地点选择
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(40.dp)
                .padding(top = 5.dp, bottom = 5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.width(20.dp))

                locationList.value.forEach { location ->
                    val backgroundColor =
                        if (location == currentLocation.value) MaterialTheme.colorScheme.primary else Color.Transparent
                    Text(
                        modifier = Modifier
                            .clickable {
                                currentLocation.value = location
                            }
                            .width(80.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(backgroundColor)
                            .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp),
                        text = location,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(800),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
            }

            // 列表左右阴影
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                drawRect(
                    brush = Brush.horizontalGradient(
                        colorStops = arrayOf(
                            0.0f to shadowColor,     // 顶部 0%
                            0.03f to Color.Transparent,
                            0.97f to Color.Transparent,  // 中间 50%
                            1.0f to shadowColor    // 底部 100%
                        )
                    ),
                    size = size
                )
            }
        }

        // 提示栏，可筛选时间段
        Row(
            modifier = Modifier
                .padding(top = 5.dp, start = 15.dp, end = 15.dp)
                .height(30.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "教室",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .weight(5f)
                    .padding(start = 8.dp)
            ) {
                periodStatus.forEachIndexed { index, status ->
                    val fontColor =
                        if (status) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                    val backgroundColor =
                        if (status) MaterialTheme.colorScheme.primary else Color.Transparent
                    Box(
                        modifier = Modifier
                            .clickable {
                                periodStatus[index] = !periodStatus[index]
                            }
                            .padding(start = 5.dp, end = 5.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .background(backgroundColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = periodStrList[index],
                            color = fontColor,
                        )
                    }
                }
            }
        }

        // 空教室列表的Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
        ) { page ->
            if (page == 0) {
                ClassroomSubScreen(
                    list = currentLocation.value?.let { todayClassroomMap[it] } ?: listOf(),
                    checkStatus = periodStatus
                )
            } else {
                ClassroomSubScreen(
                    list = currentLocation.value?.let { tomorrowClassroomMap[it] } ?: listOf(),
                    checkStatus = periodStatus
                )
            }
        }
    }
}