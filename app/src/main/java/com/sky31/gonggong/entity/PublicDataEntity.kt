package com.sky31.gonggong.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PublicDataEntity(
    @PrimaryKey val id: Int = 1,
    @Embedded val todayClassroom: ClassroomData,
    @Embedded val tomorrowClassroom: ClassroomData,
    @Embedded val calendar: CalendarData
)
