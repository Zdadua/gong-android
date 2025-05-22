package com.sky31.gonggong.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.database.CourseEntity
import com.sky31.gonggong.entity.database.PublicEntity

@Dao
interface CourseDao {
    @Query("SELECT * FROM course_data")
    suspend fun getCourseData(): CourseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(data: CourseEntity)

    @Query("SELECT calendar FROM public_data")
    suspend fun getCalendar(): CalendarData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPublicData(data: PublicEntity)

    @Query("UPDATE public_data SET calendar = :calendar")
    suspend fun updateCalendar(calendar: CalendarData)

    @Query("DELETE FROM course_data")
    suspend fun clearAll()
}