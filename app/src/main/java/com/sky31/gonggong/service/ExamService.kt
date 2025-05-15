package com.sky31.gonggong.service

import com.sky31.gonggong.entity.ApiResponse
import com.sky31.gonggong.entity.ExamData
import retrofit2.Response
import retrofit2.http.GET

interface ExamService {
    @GET("exams")
    suspend fun getExams(): Response<ApiResponse<ExamData>>
}