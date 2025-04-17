package com.sky31.gonggong.dao

import androidx.room.Dao
import androidx.room.Query
import com.sky31.gonggong.entity.RankData
import com.sky31.gonggong.entity.ScoreData
import com.sky31.gonggong.entity.database.AcademicEntity

@Dao
interface AcademicDao {
    @Query("SELECT * from academic_data")
    suspend fun getAcademicData(): AcademicEntity?

    @Query("INSERT INTO academic_data(uid) VALUES(:uid)")
    suspend fun insertEmptyAcademicData(uid: String):Long

    @Query("DELETE FROM academic_data WHERE uid = :uid")
    suspend fun deleteAcademicDataByUid(uid: String): Int

    @Query("UPDATE academic_data SET total_rank = :rank")
    suspend fun updateTotalRank(rank: RankData): Int

    @Query("UPDATE academic_data SET compulsory_rank = :rank")
    suspend fun updateCompulsoryRank(rank: RankData): Int

    @Query("UPDATE academic_data SET major_score = :score")
    suspend fun updateMajorScore(score: ScoreData): Int

    @Query("UPDATE academic_data SET minor_score = :score")
    suspend fun updateMinorScore(score: ScoreData): Int
}