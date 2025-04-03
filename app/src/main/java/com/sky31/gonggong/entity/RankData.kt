package com.sky31.gonggong.entity

import com.google.gson.annotations.SerializedName

data class RankData(
    @SerializedName("average_score")
    val averageScore: String,
    val gpa: String,
    @SerializedName("class_rank")
    val classRank: Int,
    @SerializedName("major_rank")
    val majorRank: Int,
    val terms: List<String>
)
