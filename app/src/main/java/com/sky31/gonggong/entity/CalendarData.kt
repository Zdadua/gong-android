package com.sky31.gonggong.entity

import com.google.gson.annotations.SerializedName

data class CalendarData(
    val start: String,
    val weeks: Int,
    @SerializedName("term_id")
    val termId: String
)
