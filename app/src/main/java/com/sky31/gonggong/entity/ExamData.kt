package com.sky31.gonggong.entity

import com.google.gson.annotations.SerializedName

data class ExamData(
    val name: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String,
    val location: String,
    val type: String,
)
