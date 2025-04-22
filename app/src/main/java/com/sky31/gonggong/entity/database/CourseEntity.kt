package com.sky31.gonggong.entity.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sky31.gonggong.entity.CourseData

@Entity(tableName = "course_data")
data class CourseEntity(
    @PrimaryKey val uid: Int = 1,
    val courses: Map<String, List<CourseData.CourseElem>>?,
)
