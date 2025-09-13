package com.sky31.gonggong.ui.screens.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky31.gonggong.entity.CourseData
import com.sky31.gonggong.utils.TimeUtil
import java.time.LocalDateTime

/**
 * mainScreen课程容器
 *
 * @param course 课程数据
 * @param currentTime 当前时间
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CourseBox(course: CourseData.CourseElem, currentTime: LocalDateTime) {
    // 课程容器bar的颜色
    val courseColor = remember(currentTime) {
        val courseState = TimeUtil.getCourseState(currentTime, course.startTime, course.duration)
        TimeUtil.getCourseColor(courseState)
    }

    // 课程时间
    val courseTime = remember { mutableStateOf(TimeUtil.getCourseTime(course, currentTime)) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(top = 5.dp, bottom = 5.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 课程时间
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1.2f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = TimeUtil.customTimeToString(courseTime.value[0]),
                fontWeight = FontWeight(600),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = TimeUtil.customTimeToString(courseTime.value[1]),
                fontWeight = FontWeight(600),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // color bar
        Box(
            modifier = Modifier
                .fillMaxHeight(.8f)
                .width(25.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(courseColor)
                .align(Alignment.CenterVertically)
        )

        // 课程主要信息
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = course.name,
                fontWeight = FontWeight(600),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                letterSpacing = 1.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = course.classroom,
                fontWeight = FontWeight(500),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 1.sp
            )
        }
    }
}