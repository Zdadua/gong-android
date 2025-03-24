package com.sky31.gonggong.entity

import com.google.gson.annotations.SerializedName

data class CoursesData(
    val courses: List<CourseData>
) {
    data class CourseData(
        val name: String,
        val teacher: String,
        val classroom: String,
        val weeks: String,
        @SerializedName("start_time")
        val startTime: Int,
        val duration: Int,
        val day: String
    )
}


