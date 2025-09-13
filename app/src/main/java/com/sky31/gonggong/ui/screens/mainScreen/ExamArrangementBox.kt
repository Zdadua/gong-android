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
import androidx.compose.ui.unit.dp
import com.sky31.gonggong.R
import com.sky31.gonggong.model.state.AnimationState
import com.sky31.gonggong.model.state.DataState
import com.sky31.gonggong.ui.components.LoadingRing
import com.sky31.gonggong.ui.compositionLocal.LocalNavController
import com.sky31.gonggong.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExamArrangementBox(
    viewModel: MainViewModel
) {
    val navController = LocalNavController.current
    val scope = rememberCoroutineScope()

    val currentTime by viewModel.currentTime
    val examList by viewModel.examList
    var examListState by remember { mutableStateOf<DataState>(DataState.Uninitialized) }
    val refreshState by remember {
        derivedStateOf {
            when (examListState) {
                is DataState.Loading -> AnimationState.Loading
                is DataState.Uninitialized -> AnimationState.Unstarted
                else -> AnimationState.Finished
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.updateExamList(
            init = {
                examListState = DataState.Loading
            },
            finished = fun(state: DataState) {
                examListState = state
            }
        )
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
                text = "考试安排",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            IconButton(
                onClick = {
                    scope.launch {
                        viewModel.updateExamList(
                            init = {
                                examListState = DataState.Loading
                            },
                            finished = fun(state) {
                                examListState = state
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
                    navController.navigate("academicScreen")
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.score),
                    contentDescription = "course",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }

        // 考试列表容器
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 5.dp, end = 5.dp),
            contentAlignment = Alignment.Center
        ) {

            if (examList.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "暂无考试",
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
                                painter = painterResource(R.drawable.score),
                                contentDescription = "course",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(20.dp)
                            )

                            Text(
                                text = "成绩单",
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

                    items(examList) { exam ->
                        ExamBox(exam, currentTime)
                    }

                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}