package com.sky31.gonggong.ui.screens.classroomScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky31.gonggong.entity.ClassroomData

/**
 * 空教室列表子组件
 *
 * @param list 空教室列表
 * @param checkStatus 筛选时间段数组
 */
@Composable
fun ClassroomSubScreen(
    list: List<ClassroomData.ClassroomInfo>,
    checkStatus: List<Boolean>
) {
    val listState = rememberLazyListState()

    LaunchedEffect(checkStatus) {
        listState.scrollToItem(0)
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp, bottom = 5.dp, start = 15.dp, end = 15.dp)
    ) {
        items(list) { classroomInfo ->
            // 根据要求的时间段，筛选空课教室
            for ((index, status) in checkStatus.withIndex()) {
                if (status && classroomInfo.status[index] != "空") return@items
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(top = 4.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 教室名称
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = classroomInfo.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                // 空课情况显示
                // 封装一层Box是为了背景的圆角能在1-2/9-11空课的情况下的圆角能重合
                Box(
                    modifier = Modifier
                        .weight(5f)
                        .fillMaxHeight()
                        .padding(start = 8.dp, top = 12.dp, bottom = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp)
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.onSurfaceVariant)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        classroomInfo.status.forEach { status ->
                            val color =
                                if (status == "空") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            Spacer(
                                modifier = Modifier
                                    .padding(start = 12.dp, end = 12.dp)
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(color)
                            )
                        }
                    }
                }
            }
        }
    }
}