package com.sky31.gonggong.service

import android.util.Log
import com.sky31.gonggong.dao.AcademicDao
import com.sky31.gonggong.entity.database.AcademicEntity

class DealAcademicService(service: AcademicService, dao: AcademicDao) : DealRequestService() {
    private val apiService = service
    private val academicDao = dao

    suspend fun getScores(): RequestResult {
        return getAndStore(
            apiCall = { apiService.getScores() },
            storage = { data ->
                data?.data?.let {
                    if (academicDao.getAcademicData() == null) {
                        Log.d(TAG, "insert academic data")
                        academicDao.insertAcademicData(
                            AcademicEntity(
                                1,
                                totalRank = null,
                                compulsoryRank = null,
                                majorScore = it,
                                minorScore = null,
                            )
                        )
                    } else {
                        Log.d(TAG, "update academic data")
                        academicDao.updateMajorScore(it)
                    }
                }
            }
        )
    }

    suspend fun getTotalRank(): RequestResult {
        return getAndStore(
            apiCall = { apiService.getTotalRank() },
            storage = { data ->
                data?.data?.let {
                    if (academicDao.getAcademicData() == null) {
                        Log.d(TAG, "insert academic data")
                        academicDao.insertAcademicData(
                            AcademicEntity(
                                1,
                                totalRank = it,
                                compulsoryRank = null,
                                majorScore = null,
                                minorScore = null,
                            )
                        )
                    } else {
                        Log.d(TAG, "update academic data")
                        academicDao.updateTotalRank(it)
                    }
                }
            }
        )
    }

    suspend fun getCompulsoryRank(): RequestResult {
        return getAndStore(
            apiCall = { apiService.getCompulsoryRank() },
            storage = { data ->
                data?.data?.let {
                    if (academicDao.getAcademicData() == null) {
                        Log.d(TAG, "insert academic data")
                        academicDao.insertAcademicData(
                            AcademicEntity(
                                1,
                                totalRank = null,
                                compulsoryRank = it,
                                majorScore = null,
                                minorScore = null,
                            )
                        )
                    } else {
                        Log.d(TAG, "update academic data")
                        academicDao.updateCompulsoryRank(it)
                    }
                }
            }
        )
    }

    suspend fun getMajorScoreFromDatabase() = academicDao.getMajorScore()
    suspend fun getCompulsoryRankFromDatabase() = academicDao.getCompulsoryRank()
    suspend fun getTotalRankFromDatabase() = academicDao.getTotalRank()
}