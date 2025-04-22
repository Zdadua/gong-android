package com.sky31.gonggong.viewmodel

import androidx.lifecycle.ViewModel
import com.sky31.gonggong.MainApplication
import com.sky31.gonggong.service.CourseService
import com.sky31.gonggong.service.DealCourseService

class CourseViewModel: ViewModel() {
    private val courseDao by lazy { MainApplication.appDatabase.getCourseDao() }
    private val dealCourseService by lazy {
        val service = MainApplication.retrofit.create(CourseService::class.java)
        DealCourseService(service, courseDao)
    }

    suspend fun getCourse() = dealCourseService.getCourse()
}