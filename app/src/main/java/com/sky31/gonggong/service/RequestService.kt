package com.sky31.gonggong.service

import com.sky31.gonggong.entity.ApiLoginResponse
import com.sky31.gonggong.entity.ApiResponse
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.ClassroomData
import com.sky31.gonggong.entity.CourseData
import com.sky31.gonggong.entity.ExamData
import com.sky31.gonggong.entity.InfoData
import com.sky31.gonggong.entity.RankData
import com.sky31.gonggong.entity.ScoreData
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RequestService {
    @POST("login")
    @FormUrlEncoded
    suspend fun login(@Field("username") username: String, @Field("password") password: String): Response<ApiLoginResponse>

    @GET("info")
    suspend fun getInfo(): Response<ApiResponse<InfoData>>

    @GET("courses")
    suspend fun getCourses(): Response<ApiResponse<CourseData>>

    @GET("scores")
    suspend fun getMajorScores(): Response<ApiResponse<ScoreData>>

    @GET("minor/scores")
    suspend fun getMinorScores(): Response<ApiResponse<ScoreData>>

    @GET("exams")
    suspend fun getExams(): Response<ApiResponse<ExamData>>

    @GET("rank")
    suspend fun getTotalRank(): Response<ApiResponse<RankData>>

    @GET("compulsory/rank")
    suspend fun getCompulsoryRank(): Response<ApiResponse<RankData>>

    @GET("calendar")
    suspend fun getCalendar(): Response<ApiResponse<CalendarData>>

    @GET("classroom/today")
    suspend fun getTodayClassroom(): Response<ApiResponse<ClassroomData>>

    @GET("classroom/tomorrow")
    suspend fun getTomorrowClassroom(): Response<ApiResponse<ClassroomData>>
}