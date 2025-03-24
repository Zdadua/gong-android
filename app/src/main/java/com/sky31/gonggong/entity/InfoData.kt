package com.sky31.gonggong.entity

import com.google.gson.annotations.SerializedName

data class InfoData(
    @SerializedName("student_id")
    val studentId: String,
    val name: String,
    val gender: String,
    val birthday: String,
    val major: String,
    val clazz: String,
    @SerializedName("entrance_day")
    val entranceDay: String,
    val college: String
)
