package com.sky31.gonggong.service

import android.util.Log
import com.sky31.gonggong.dao.ExamDao
import com.sky31.gonggong.entity.database.ExamEntity

class DealExamService(service: ExamService, dao: ExamDao): DealRequestService() {
    private var apiService = service
    private val examDao = dao

    suspend fun getExams(): RequestResult {
        return getAndStore(
            apiCall = { apiService.getExams() },
            storage = { data ->
                Log.d(TAG, "insert exams")
                examDao.insertExamList(ExamEntity(exams = data?.data?.exams))
            }
        )
    }

    fun setService(service: ExamService) {
        apiService = service
        Log.i(TAG, "reset examService")
    }

    suspend fun getExamsFromDatabase(): ExamEntity? = examDao.getExamList()
}