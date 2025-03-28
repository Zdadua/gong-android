package com.sky31.gonggong.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SchoolDataEntity(
    @PrimaryKey val uid: String,
    @Embedded val totalRank: RankData,
    @Embedded val compulsoryRank: RankData,
    @Embedded val courses: CourseData,
    @Embedded val majorScore: ScoreData,
    @Embedded val minorScore: ScoreData,
)
