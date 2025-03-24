package com.sky31.gonggong.entity

import com.google.gson.annotations.SerializedName

data class ScoreData(
    @SerializedName("student_id")
    val studentId: String,
    val name: String,
    val college: String,
    val major: String,
    val scores: List<ScoreElem>,
    @SerializedName("total_credit")
    val totalCredit: List<String>,
    @SerializedName("elective_credit")
    val electiveCredit: List<String>,
    @SerializedName("compulsory_credit")
    val compulsoryCredit: List<String>,
    @SerializedName("cross_course_credit")
    val crossCourseCredit: List<String>,
    @SerializedName("average_score")
    val averageScore: String,
    val gpa: String,
    val cet4: String,
    val cet6: String
) {
    data class ScoreElem(
        val name: String,
        val score: String,
        val credit: String,
        val type: String,
        val term: Int
    )
}


