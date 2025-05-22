package com.sky31.gonggong.service

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.sky31.gonggong.dao.CourseDao
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.CourseData
import com.sky31.gonggong.entity.database.CourseEntity
import com.sky31.gonggong.entity.database.PublicEntity
import com.sky31.gonggong.utils.TimeUtil.isInThisWeek
import com.sky31.gonggong.utils.TimeUtil.weekdayNameMap
import java.time.LocalDate

/**
 * 将courseList转为键为String(weekday)，值为List<CourseElem>的Map
 */
@RequiresApi(Build.VERSION_CODES.O)
fun toCourseMap(courses: List<CourseData.CourseElem>): Map<String, List<CourseData.CourseElem>> {
    val map = mutableMapOf<String, List<CourseData.CourseElem>>()

    weekdayNameMap.forEach { (_, s) ->
        map[s] = listOf()
    }

    for(course in courses) {
        val key = course.day
        map[key] = map[key]?.plus(course) ?: listOf(course)
    }

    return map
}

class DealCourseService(service: CourseService, dao: CourseDao): DealRequestService() {
    private var apiService = service
    private val courseDao = dao

    /**
     * 获取课程表并存入数据库
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getCourses(): RequestResult {
        return getAndStore(
            apiCall = { apiService.getCourses() },
            storage = { data ->
                Log.d(TAG, "insert course")
                courseDao.insertCourse(CourseEntity(courses = data?.data?.let { toCourseMap(it.courses) }))
            }
        )
    }

    /**
     * 获取课程表日历并存入数据库
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getCalendar(): RequestResult {
        return getAndStore(
            apiCall = {apiService.getCalendar()},
            storage = { data ->
                data?.data?.let {
                    if(courseDao.getCalendar() == null) {
                        Log.d(TAG, "insert public data")
                        courseDao.insertPublicData(PublicEntity(1, null, null, it))
                    }
                    else {
                        Log.d(TAG, "update calendar")
                        courseDao.updateCalendar(it)
                    }

                }
            }
        )
    }

    /**
     * 获取今天的课程表
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getTodayCourseList(): List<CourseData.CourseElem> {
        val result = mutableListOf<CourseData.CourseElem>()

        val calendar = courseDao.getCalendar() ?: return result
        val courses = courseDao.getCourseData()?.courses ?: return result

        val start = LocalDate.parse(calendar.start)
        val dateTime = LocalDate.now()

        val week = (dateTime.toEpochDay() - start.toEpochDay()) / 7 + 1

        val courseList = courses[weekdayNameMap[dateTime.dayOfWeek.value]]
        if (courseList != null) {
            for(course in courseList) {
                if(isInThisWeek(week, course))
                    result.add(course)
            }
        }

        return result
    }

    suspend fun getCourseMap(): Map<String, List<CourseData.CourseElem>>? {
        return courseDao.getCourseData()?.courses
    }

    fun setService(service: CourseService) {
        apiService = service
        Log.i(TAG, "reset courseService")
    }

    /**
     * 从数据库获取校历信息
     */
    suspend fun getCalendarFromDatabase(): CalendarData? = courseDao.getCalendar()
}