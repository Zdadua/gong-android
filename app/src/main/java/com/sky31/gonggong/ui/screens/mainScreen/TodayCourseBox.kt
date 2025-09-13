package com.sky31.gonggong.ui.screens.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky31.gonggong.R
import com.sky31.gonggong.model.state.AnimationState
import com.sky31.gonggong.model.state.DataState
import com.sky31.gonggong.ui.components.CircleProgressBar
import com.sky31.gonggong.ui.components.LoadingRing
import com.sky31.gonggong.ui.compositionLocal.LocalNavController
import com.sky31.gonggong.utils.TimeUtil
import com.sky31.gonggong.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayCourseBox(
    viewModel: MainViewModel
) {
    val navController = LocalNavController.current

    val scope = rememberCoroutineScope()

    // 已完成课程进度
    val progression by viewModel.progression

    val completedCourseNum by viewModel.completedCourseNum
    val currentTime by viewModel.currentTime
    val courseList by viewModel.courseList

    var courseListState by remember { mutableStateOf<DataState>(DataState.Uninitialized) }
    val refreshState by remember {
        derivedStateOf {
            when (courseListState) {
                is DataState.Loading -> AnimationState.Loading
                is DataState.Uninitialized -> AnimationState.Unstarted
                else -> AnimationState.Finished
            }
        }
    }

    // 获取课表数据
    LaunchedEffect(Unit) {
        viewModel.updateCourseList(
            init = {
                courseListState = DataState.Loading
            },
            finished = fun(state: DataState) {
                courseListState = state
            }
        )
    }

    // 更新进度条
    LaunchedEffect(courseList, currentTime) {
        if (courseListState == DataState.Loading || courseListState == DataState.Uninitialized) {
            return@LaunchedEffect
        }
        if (courseList.isEmpty()) {
            viewModel.setProgression(1f)
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
        viewModel.setProgression(accomplishment / courseList.size)
        viewModel.setCompletedNum(accomplishment.toInt())
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        // title栏
        Row(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = "今日课程",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
            )

            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .size(42.dp)
            ) {
                CircleProgressBar(
                    target = progression,
                    courseCount = courseList.size
                )
            }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "共",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    Text(
                        text = "${courseList.size}",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight(800),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp)
                    )

                    Text(
                        text = "节课",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "已上",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    Text(
                        text = "$completedCourseNum",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight(800),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp)
                    )

                    Text(
                        text = "节课",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            IconButton(
                onClick = {
                    scope.launch {
                        viewModel.updateCourseList(
                            init = {
                                courseListState = DataState.Loading
                            },
                            finished = fun(state) {
                                courseListState = state
                            }
                        )
                    }
                }
            ) {
                LoadingRing(
                    state = refreshState
                )
            }

            IconButton(
                onClick = {
                    navController.navigate("courseScreen")
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.course),
                    contentDescription = "course",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }

        // 课程容器
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 5.dp, end = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            if (courseList.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "今日无课",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Button(
                        onClick = {
                            navController.navigate("courseScreen")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,  // 背景透明
                            contentColor = Color.Unspecified
                        ),
                        contentPadding = PaddingValues(start = 25.dp, end = 25.dp),
                        modifier = Modifier.defaultMinSize(0.dp, 0.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.course),
                                contentDescription = "course",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(20.dp)
                            )

                            Text(
                                text = "课程表",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    items(courseList) { course ->
                        CourseBox(course, currentTime)
                    }
                }
            }
        }
    }
}