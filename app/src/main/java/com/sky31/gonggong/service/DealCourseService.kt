package com.sky31.gonggong.service

import androidx.compose.ui.graphics.Color
import com.sky31.gonggong.dao.CourseDao
import com.sky31.gonggong.entity.ApiResponse
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.CourseData
import com.sky31.gonggong.entity.database.CourseEntity
import com.sky31.gonggong.ui.theme.CourseColor

fun generateCourseColor(courseName: String): Color {
    val hash = courseName.hashCode()
    val colors = CourseColor.entries.toTypedArray()

    return Color(colors[hash % 10].rgb)
}

fun isInThisWeek(week: Int, course: CourseData.CourseElem): Boolean {
    val weeks = course.weeks.split(",")

    for(weekStr in weeks) {
        if(weekStr.length == 1 && weekStr.toInt() == week)
            return true

        if(weekStr[0].code - 48 <= week && weekStr[2].code - 48 >= week)
            return true
    }

    return false
}

fun toCourseMap(courses: List<CourseData.CourseElem>): Map<String, List<CourseData.CourseElem>> {
    val map = mutableMapOf<String, List<CourseData.CourseElem>>()

    for(course in courses) {
        val key = course.day
        map[key] = map[key]?.plus(course) ?: listOf(course)
    }

    return map
}

class DealCourseService(service: CourseService, dao: CourseDao): DealRequestService() {
    private val apiService = service
    private val courseDao = dao

    /**
     * 互殴课程表并存入数据库
     */
    suspend fun getCourse(): Boolean {
        return getAndStore(
            apiCall = { apiService.getCourses() },
            storage = { data ->
                courseDao.insertCourse(CourseEntity(1, data?.data?.let { toCourseMap(it.courses) }))
            }
        )
    }

    /**
     * 获取课程表日历并存入数据库
     */
    suspend fun getCalendar(): Boolean {
        return getAndStore(
            apiCall = {apiService.getCalendar()},
            storage = { data ->
                data?.data?.let { courseDao.updateCalendar(it) }
            }
        )
    }

    /**
     * 从数据库获取课程表
     */
    suspend fun getCourseFromDatabase(): Map<String, List<CourseData.CourseElem>>? {
        val result = courseDao.getCourseData()

        return result?.courses
    }

    /**
     * 从数据库获取校历信息
     */
    suspend fun getCalendarFromDatabase(): CalendarData? = courseDao.getCalendar()
}