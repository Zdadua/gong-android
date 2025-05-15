package com.sky31.gonggong.ui.screens.courseScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sky31.gonggong.R
import com.sky31.gonggong.ui.DataState
import com.sky31.gonggong.ui.theme.LocalThemeColor
import com.sky31.gonggong.ui.theme.Orange01
import com.sky31.gonggong.utils.TimeUtil
import com.sky31.gonggong.viewmodel.CourseViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.format
import java.time.LocalDate

/**
 * 课程表页面
 *
 * @param navController 导航控制器
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CourseScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val viewModel: CourseViewModel = viewModel()
    val curWeekNum by viewModel.curWeekNum
    val calendar by viewModel.calendar

    // 课程表数据状态
    val tableState by viewModel.courseTableState

    // 周次选择列表状态
    var weekListState by remember { mutableStateOf(false) }

    // 数据状态icon id
    val id by remember {
        derivedStateOf {
            when (tableState) {
                is DataState.Uninitialized -> R.drawable.expired
                is DataState.Newest -> R.drawable.newest
                is DataState.Expired -> R.drawable.expired
                is DataState.Loading -> R.drawable.expired
                is DataState.Error -> R.drawable.error
            }
        }
    }

    // HorizontalPager的状态
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { calendar?.weeks ?: 0 }
    )

    LaunchedEffect(Unit) {
        viewModel.updateData()
        viewModel.getWeekNum()
        pagerState.scrollToPage(curWeekNum.toInt() - 1)
    }

    Scaffold(
        modifier = Modifier
            .safeDrawingPadding(),
        topBar = {
            // 用于解决text和周次选择列表无法居中的问题
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Orange01)
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // 切换animate
                    AnimatedContent(
                        targetState = weekListState,
                        transitionSpec = {
                            fadeIn(tween(300)) togetherWith
                                    fadeOut(tween(300)) using SizeTransform(clip = false)
                        },
                        label = "animatedWeekList",
                    ) { targetState ->
                        if (targetState) {
                            if (calendar != null) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp, end = 10.dp)
                                        .horizontalScroll(rememberScrollState())
                                ) {
                                    Spacer(modifier = Modifier.width(25.dp))
                                    for (index in 1..calendar!!.weeks)
                                        Text(
                                            modifier = Modifier
                                                .padding(start = 10.dp, end = 10.dp)
                                                .clickable {
                                                    scope.launch { pagerState.scrollToPage(index - 1) }
                                                    weekListState = false
                                                },
                                            text = "$index",
                                            color = Color.White
                                        )

                                    Spacer(modifier = Modifier.width(25.dp))
                                }
                            }
                        } else {
                            Text(
                                modifier = Modifier
                                    .clickable { weekListState = true },
                                text = "第${pagerState.currentPage + 1}周",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        drawRect(
                            brush = Brush.horizontalGradient(
                                colorStops = arrayOf(
                                    0.0f to Orange01,
                                    0.15f to Color.Transparent,
                                    0.70f to Color.Transparent,
                                    0.85f to Orange01,
                                )
                            ),
                            size = size
                        )
                    }
                }
                // icon row 回退、日历下载、状态的层次
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .padding(5.dp)
                            .width(20.dp)
                            .height(20.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { navController.navigate("main") },
                        painter = painterResource(id = R.drawable.left_arrow),
                        contentDescription = "left_arrow"
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(20.dp)
                            .height(20.dp)
                            .clickable {
                                navController.navigate("main")
                            },
                        painter = painterResource(id = R.drawable.baseline_edit_calendar_24),
                        contentDescription = "calendar"
                    )

                    Image(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .clickable {
                                navController.navigate("main")
                            },
                        painter = painterResource(id = id),
                        contentDescription = "state"
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            modifier = Modifier
                .padding(innerPadding),
            state = pagerState,
        ) { page ->
            val courseMap = viewModel.getWeekCourseMap(page.toLong() + 1)
            println(courseMap.toString())

            if (calendar != null) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(LocalThemeColor.current.backgroundColor),
                ) {
                    // 左侧时间表
                    Column(
                        modifier = Modifier
                            .width(42.dp)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val weekStart = LocalDate.parse(calendar!!.start).atStartOfDay()
                            .plusWeeks(page.toLong())
                        val startTime = TimeUtil.getStartTime(weekStart)

                        // 月份box
                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .fillMaxWidth()
                                .background(LocalThemeColor.current.boxColorPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${weekStart.monthValue}",
                                color = LocalThemeColor.current.textPrimary
                            )
                        }

                        startTime.forEachIndexed { index, start ->
                            val end = TimeUtil.CustomTime(
                                hour = start.hour + (start.minute + 45) / 60,
                                minute = (start.minute + 45) % 60
                            )
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .padding(top = 1.dp, bottom = 1.dp)
                                    .background(LocalThemeColor.current.boxColorPrimary),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = TimeUtil.customTimeToString(start),
                                    color = LocalThemeColor.current.textPrimary
                                )
                                Text(
                                    text = TimeUtil.customTimeToString(end),
                                    color = LocalThemeColor.current.textPrimary
                                )
                            }

                            // 中午和晚上的间隔
                            if (index == 3 || index == 7) {
                                Spacer(
                                    modifier = Modifier
                                        .height(5.dp)
                                        .fillMaxWidth()
                                        .background(Orange01)
                                )
                            }
                        }
                    }

                    // 遍历周一至周日
                    courseMap.keys.forEachIndexed { index, item ->
                        CourseColumn(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            courseList = courseMap[item]?.sortedBy { course -> course.startTime }
                                ?: listOf(),
                        ) {
                            Column(
                                modifier = Modifier
                                    .height(40.dp)
                                    .fillMaxWidth()
                                    .padding(start = 1.dp, end = 1.dp)
                                    .background(LocalThemeColor.current.boxColorPrimary),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                val date = LocalDate.parse(calendar!!.start).atStartOfDay()
                                    .plusWeeks(page.toLong()).plusDays(index.toLong())

                                Text(
                                    text = TimeUtil.weekdayNameMapCN.getValue(
                                        TimeUtil.reverseWeekdayNameMap.getValue(
                                            item
                                        )
                                    ),
                                    color = LocalThemeColor.current.textPrimary
                                )

                                Text(
                                    text = "${format("%02d", date.monthValue)}-${
                                        format(
                                            "%02d",
                                            date.dayOfMonth
                                        )
                                    }",
                                    fontSize = 12.sp,
                                    color = LocalThemeColor.current.textSecondary
                                )
                            }
                        }
                    }
                }
            }


        }
    }

    // 解决Status bar的颜色
    // TODO 暂时未找到更好的方法，解决Status bar的颜色和topBar相同
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Orange01)
        )
    }
}