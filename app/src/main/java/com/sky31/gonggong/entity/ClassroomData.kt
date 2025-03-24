package com.sky31.gonggong.entity

data class ClassroomData(
    val date: String,
    val classrooms: Map<String, List<ClassroomInfo>>
) {
    data class ClassroomInfo(
        val name: String,
        val status: List<String>
    )
}
