package com.sky31.gonggong.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sky31.gonggong.entity.RankData
import com.sky31.gonggong.entity.ScoreData

@Entity(tableName = "academic_data")
data class AcademicEntity(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "total_rank") val totalRank: RankData?,
    @ColumnInfo(name = "compulsory_rank") val compulsoryRank: RankData?,
    @ColumnInfo(name = "major_score") val majorScore: ScoreData?,
    @ColumnInfo(name = "minor_score") val minorScore: ScoreData?
)