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
    @FormUrlEncoded
    suspend fun getInfo(): Response<ApiResponse<InfoData>>

    @GET("courses")
    @FormUrlEncoded
    suspend fun getCourses(): Response<ApiResponse<CourseData>>

    @GET("scores")
    @FormUrlEncoded
    suspend fun getMajorScores(): Response<ApiResponse<ScoreData>>

    @GET("minor/scores")
    @FormUrlEncoded
    suspend fun getMinorScores(): Response<ApiResponse<ScoreData>>

    @GET("exams")
    @FormUrlEncoded
    suspend fun getExams(): Response<ApiResponse<ExamData>>

    @GET("rank")
    @FormUrlEncoded
    suspend fun getTotalRank(): Response<ApiResponse<RankData>>

    @GET("compulsory/rank")
    @FormUrlEncoded
    suspend fun getCompulsoryRank(): Response<ApiResponse<RankData>>

    @GET("calendar")
    @FormUrlEncoded
    suspend fun getCalendar(): Response<ApiResponse<CalendarData>>

    @GET("classroom/today")
    @FormUrlEncoded
    suspend fun getTodayClassroom(): Response<ApiResponse<ClassroomData>>

    @GET("classroom/tomorrow")
    @FormUrlEncoded
    suspend fun getTomorrowClassroom(): Response<ApiResponse<ClassroomData>>
}