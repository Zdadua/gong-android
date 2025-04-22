package com.sky31.gonggong.service

import com.sky31.gonggong.entity.ApiResponse
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.CourseData
import retrofit2.Response
import retrofit2.http.GET

interface CourseService {
    @GET("courses")
    suspend fun getCourses(): Response<ApiResponse<CourseData>>

    @GET("calendar")
    suspend fun getCalendar(): Response<ApiResponse<CalendarData>>
}