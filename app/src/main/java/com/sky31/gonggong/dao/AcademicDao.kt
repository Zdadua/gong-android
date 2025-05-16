package com.sky31.gonggong.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sky31.gonggong.entity.RankData
import com.sky31.gonggong.entity.ScoreData
import com.sky31.gonggong.entity.database.AcademicEntity

@Dao
interface AcademicDao {
    @Query("SELECT * from academic_data")
    suspend fun getAcademicData(): AcademicEntity?

    @Query("SELECT major_score from academic_data")
    suspend fun getMajorScore(): ScoreData?

    @Query("SELECT compulsory_rank from academic_data")
    suspend fun getCompulsoryRank(): RankData?

    @Query("SELECT total_rank from academic_data")
    suspend fun getTotalRank(): RankData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAcademicData(data: AcademicEntity)

    @Query("UPDATE academic_data SET total_rank = :rank")
    suspend fun updateTotalRank(rank: RankData): Int

    @Query("UPDATE academic_data SET compulsory_rank = :rank")
    suspend fun updateCompulsoryRank(rank: RankData): Int

    @Query("UPDATE academic_data SET major_score = :score")
    suspend fun updateMajorScore(score: ScoreData): Int

    @Query("UPDATE academic_data SET minor_score = :score")
    suspend fun updateMinorScore(score: ScoreData): Int
}