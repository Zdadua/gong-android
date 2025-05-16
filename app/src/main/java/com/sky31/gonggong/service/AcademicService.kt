package com.sky31.gonggong.service

import com.sky31.gonggong.entity.ApiResponse
import com.sky31.gonggong.entity.RankData
import com.sky31.gonggong.entity.ScoreData
import retrofit2.Response
import retrofit2.http.GET

interface AcademicService {
    @GET("scores")
    suspend fun getScores(): Response<ApiResponse<ScoreData>>

    @GET("minor/scores")
    suspend fun getMinorScores(): Response<ApiResponse<ScoreData>>

    @GET("rank")
    suspend fun getTotalRank(): Response<ApiResponse<RankData>>

    @GET("compulsory/rank")
    suspend fun getCompulsoryRank(): Response<ApiResponse<RankData>>
}