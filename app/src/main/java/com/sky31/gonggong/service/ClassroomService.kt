package com.sky31.gonggong.service

import com.sky31.gonggong.entity.ApiResponse
import com.sky31.gonggong.entity.ClassroomData
import retrofit2.Response
import retrofit2.http.GET

interface ClassroomService {
    @GET("classroom/today")
    suspend fun getTodayClassroom(): Response<ApiResponse<ClassroomData>>

    @GET("classroom/tomorrow")
    suspend fun getTomorrowClassroom(): Response<ApiResponse<ClassroomData>>
}