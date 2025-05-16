package com.sky31.gonggong.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.sky31.gonggong.entity.CourseData
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
object TimeUtil {
    data class CustomTime(
        val hour: Int,
        val minute: Int
    )

    sealed class CourseState {
        data object Before: CourseState()
        data object During: CourseState()
        data object After: CourseState()
    }

    private val summerStartTime = listOf(
        CustomTime(8, 0),
        CustomTime(8, 55),
        CustomTime(10, 10),
        CustomTime(11, 5),
        CustomTime(14, 30),
        CustomTime(15, 25),
        CustomTime(16, 40),
        CustomTime(17, 35),
        CustomTime(19, 30),
        CustomTime(20, 25),
        CustomTime(21, 20),

    )

    private val winterStartTime = listOf(
        CustomTime(8, 0),
        CustomTime(8, 55),
        CustomTime(10, 10),
        CustomTime(11, 5),
        CustomTime(14, 0),
        CustomTime(14, 55),
        CustomTime(16, 10),
        CustomTime(17, 5),
        CustomTime(19, 0),
        CustomTime(19, 55),
        CustomTime(20, 50),
    )

    val weekdayNameMap = mapOf(
        1 to "Monday",
        2 to "Tuesday",
        3 to "Wednesday",
        4 to "Thursday",
        5 to "Friday",
        6 to "Saturday",
        7 to "Sunday"
    )

    val reverseWeekdayNameMap=  mapOf(
        "Monday" to 1,
        "Tuesday" to 2,
        "Wednesday" to 3,
        "Thursday" to 4,
        "Friday" to 5,
        "Saturday" to 6,
        "Sunday" to 7
    )

    val weekdayNameMapCN = mapOf(
        1 to "星期一",
        2 to "星期二",
        3 to "星期三",
        4 to "星期四",
        5 to "星期五",
        6 to "星期六",
        7 to "星期日"
    )

    val chineseNumberMap = mapOf(
        1 to "一",
        2 to "二",
        3 to "三",
        4 to "四",
        5 to "五",
        6 to "六",
        7 to "七",
    )

    private val courseColor = mapOf(
        CourseState.Before to Color(0xFF2FB8BE),
        CourseState.During to Color(0xFFF8931D),
        CourseState.After to Color(0xFFEF5C66)
    )

    /**
     * 判断时间是否为夏令时
     *
     * @param dateTime 时间
     */
    private fun isSummerTime(
        dateTime: LocalDateTime
    ): Boolean {
        val month = dateTime.monthValue

        return when(month) {
            in 5..9 -> true
            else -> false
        }
    }

    /**
     * 获取课程时间 {HH::mm, HH::mm}
     *
     * @param course 课程数据
     * @param currentTime 当前时间
     */
    fun getCourseTime(course: CourseData.CourseElem, currentTime: LocalDateTime): List<CustomTime> {
        val startTime = (if(isSummerTime(currentTime)) summerStartTime[course.startTime - 1] else winterStartTime[course.startTime - 1])
        val endTime = CustomTime(
            hour = (startTime.hour + course.duration.let { it * 45 + (it - 1) * 10 } / 60) % 24,
            minute = (startTime.minute + course.duration.let { it * 45 + (it - 1) * 10 }) % 60
        )

        return listOf(startTime, endTime)
    }

    /**
     * 获取课程时间字符串
     *
     * @param customTime 自定义时间
     */
    fun customTimeToString(customTime: CustomTime): String {
        return "${customTime.hour.let { if(it >= 10) it else "0${it}" } }:${customTime.minute.let { if(it >= 10) it else "0${it}" }}"
    }

    /**
     * 获取课程状态
     *
     * @param currentTime 当前时间
     * @param start 起始时间
     * @param duration 课程时长
     */
    fun getCourseState(currentTime: LocalDateTime, start: Int, duration: Int): CourseState {

        val curr = currentTime.let { currentTime.hour * 60 + currentTime.minute }
        val startTime = (if(isSummerTime(currentTime)) summerStartTime[start - 1] else winterStartTime[start - 1]).let { it.hour * 60 + it.minute }

        if(startTime > curr)
            return CourseState.After
        if(startTime + duration * 45 + 15 * (duration - 1) < curr)
            return CourseState.Before
        return CourseState.During
    }

    fun getCourseColor(state: CourseState): Color {
        return courseColor[state] ?: Color(0xFF7A7A7A)
    }

    /**
     * 判断课程是否在本周
     *
     * @param week 当前周
     * @param course 课程数据
     */
    fun isInThisWeek(week: Long, course: CourseData.CourseElem): Boolean {
        val weeks = course.weeks.split(",")

        for(weekStr in weeks) {
            if(weekStr.contains("-")) {
                val weekList = weekStr.split("-")
                if(weekList[0].toLong() <= week && weekList[1].toLong() >= week)
                    return true
            } else {
                if(weekStr.toLong() == week)
                    return true
            }
        }

        return false
    }

    /**
     * 获取开始时间为夏令时还是冬令时并返回
     *
     * @param dateTime 时间
     */
    fun getStartTime(dateTime: LocalDateTime): List<CustomTime> {
        if(isSummerTime(dateTime)) {
            println("summer")
            return summerStartTime
        }
        else {
            println("winter")
            return winterStartTime
        }
    }
}