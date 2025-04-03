package com.sky31.gonggong.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.ClassroomData

@Entity(tableName = "public_data")
data class PublicEntity(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "today_classroom") val todayClassroom: ClassroomData?,
    @ColumnInfo(name = "tomorrow_classroom") val tomorrowClassroom: ClassroomData?,
    @ColumnInfo(name = "calendar") val calendar: CalendarData?
)
