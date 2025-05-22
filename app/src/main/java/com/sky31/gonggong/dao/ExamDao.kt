package com.sky31.gonggong.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sky31.gonggong.entity.database.ExamEntity

@Dao
interface ExamDao {
    @Query("SELECT * FROM exam_data WHERE id = 1 LIMIT 1")
    suspend fun getExamList(): ExamEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExamList(examEntity: ExamEntity)

    @Query("DELETE FROM exam_data")
    suspend fun clearAll()
}