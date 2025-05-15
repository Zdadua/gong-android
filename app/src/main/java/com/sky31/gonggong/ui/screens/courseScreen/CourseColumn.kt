package com.sky31.gonggong.ui.screens.courseScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky31.gonggong.entity.CourseData
import com.sky31.gonggong.ui.theme.CourseColor
import kotlin.math.absoluteValue

/**
 * 使用courseName的哈希值生成颜色
 *
 * @param courseName 课程名称
 */
fun generateCourseColor(courseName: String): Color {
    val hash = courseName.hashCode().absoluteValue
    val colors = CourseColor.entries.toTypedArray()

    return Color(colors[hash % 10].rgb)
}

/**
 * 单列日课程列表
 *
 * @param courseList 课程数据数组
 * @param content 时间信息由外部提供
 */
@Composable
fun CourseColumn(
    modifier: Modifier = Modifier,
    courseList: List<CourseData.CourseElem> = emptyList(),
    content: @Composable () -> Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()

        // 1-4节
        CourseColumnFragment(
            courseList = courseList,
            start = 1,
            end = 4,
            modifier = Modifier.weight(4f)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 5-8节
        CourseColumnFragment(
            courseList = courseList,
            start = 5,
            end = 8,
            modifier = Modifier.weight(4f)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 9-11节
        CourseColumnFragment(
            courseList = courseList,
            start = 9,
            end = 11,
            modifier = Modifier.weight(3f)
        )
    }
}

/**
 * 单列日课程列表片段
 *
 * @param courseList 课程数据数组
 * @param start 开始时间
 * @param end 结束时间
 */
@Composable
fun CourseColumnFragment(
    modifier: Modifier = Modifier,
    courseList: List<CourseData.CourseElem>,
    start: Int,
    end: Int
) {

    Column(
        modifier = modifier
    ) {
        var index = start
        courseList
            .filter { course -> course.startTime in start..end }
            .forEach { course ->
                if (course.startTime > index) {
                    Spacer(modifier = Modifier.weight((course.startTime - index).toFloat()))
                }

                Column(
                    modifier = Modifier
                        .weight(course.duration.toFloat())
                        .padding(1.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(generateCourseColor(course.name))
                        .padding(2.dp)
                ) {
                    Text(
                        text = course.name,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = course.classroom,
                        modifier = Modifier,
                        fontSize = 13.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }

                index = course.startTime + course.duration
            }

        if (index <= end) {
            Spacer(modifier = Modifier.weight((end - index + 1).toFloat()))
        }
    }
}