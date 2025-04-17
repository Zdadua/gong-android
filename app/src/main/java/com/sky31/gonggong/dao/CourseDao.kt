package com.sky31.gonggong.dao

import androidx.room.Dao
import androidx.room.Query
import com.sky31.gonggong.entity.database.CourseEntity

@Dao
interface CourseDao {
    @Query("SELECT * FROM course_data")
    suspend fun getCourseData(): CourseEntity?
}