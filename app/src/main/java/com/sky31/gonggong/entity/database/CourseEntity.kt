package com.sky31.gonggong.entity.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sky31.gonggong.entity.CourseData

@Entity(tableName = "course_data")
data class CourseEntity(
    @PrimaryKey val uid: String,
    val courses: List<CourseData.CourseElem>,
)
