package com.sky31.gonggong.entity.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sky31.gonggong.entity.ExamData

@Entity(tableName = "exam_data")
data class ExamEntity(
    @PrimaryKey val uid: String,
    val exams: List<ExamData.ExamElem>
)
