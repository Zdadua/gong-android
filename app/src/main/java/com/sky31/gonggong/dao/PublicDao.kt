package com.sky31.gonggong.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.ClassroomData
import com.sky31.gonggong.entity.database.PublicEntity

@Dao
interface PublicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPublicData(data: PublicEntity)

    @Query("UPDATE public_data SET today_classroom = :today, tomorrow_classroom = :tomorrow, calendar = :calendar")
    suspend fun updatePublicInfo(today: ClassroomData, tomorrow: ClassroomData, calendar: CalendarData): Int

    @Query("SELECT today_classroom FROM public_data")
    suspend fun getTodayClassroom(): ClassroomData?

    @Query("UPDATE public_data SET today_classroom = :today")
    suspend fun updateTodayClassroom(today: ClassroomData): Int

    @Query("SELECT tomorrow_classroom FROM public_data")
    suspend fun getTomorrowClassroom(): ClassroomData?

    @Query("UPDATE public_data SET tomorrow_classroom = :tomorrow")
    suspend fun updateTomorrowClassroom(tomorrow: ClassroomData): Int

    @Query("UPDATE public_data SET calendar = :calendar")
    suspend fun updateCalendar(calendar: CalendarData): Int

    @Query("SELECT * FROM public_data")
    suspend fun getPublicData(): PublicEntity?

    @Query("DELETE FROM public_data")
    suspend fun clearAll()
}