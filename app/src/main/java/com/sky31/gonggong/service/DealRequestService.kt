package com.sky31.gonggong.service

import com.sky31.gonggong.dao.UserDao
import com.sky31.gonggong.entity.ApiLoginResponse
import com.sky31.gonggong.entity.ApiResponse
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.ClassroomData
import com.sky31.gonggong.entity.CourseData
import com.sky31.gonggong.entity.ExamData
import com.sky31.gonggong.entity.InfoData
import com.sky31.gonggong.entity.RankData
import com.sky31.gonggong.entity.ScoreData
import retrofit2.Response

class DealRequestService(apiService: RequestService, dao: UserDao) {
    private val requestService = apiService
    private val userDao = dao

    private suspend fun <T> getAndStore(apiCall: suspend() -> Response<T>, storage: suspend(data: T?) -> Unit): Boolean {
        val result = safeApiCall {
            apiCall()
        }

        when(result) {
            is ResultWrapper.Success -> {
                storage(result.data)
                return true
            }
            is ResultWrapper.Error -> {
                // TODO: 处理错误
                return false
            }
            is ResultWrapper.NetworkError -> {
                // TODO: 处理网络错误
                return false
            }
        }
    }

    suspend fun getInfo(): ResultWrapper<ApiResponse<InfoData>?> {
        return safeApiCall {
            requestService.getInfo()
        }
    }

    suspend fun getCourses(): ResultWrapper<ApiResponse<CourseData>?> {
        return safeApiCall {
            requestService.getCourses()
        }
    }

    suspend fun getMajorScore(): ResultWrapper<ApiResponse<ScoreData>?> {
        return safeApiCall {
            requestService.getMajorScores()
        }
    }

    suspend fun getMinorScore(): ResultWrapper<ApiResponse<ScoreData>?> {
        return safeApiCall {
            requestService.getMinorScores()
        }
    }

    suspend fun getExam(): ResultWrapper<ApiResponse<ExamData>?> {
        return safeApiCall {
            requestService.getExams()
        }
    }

    suspend fun getTotalRank(): ResultWrapper<ApiResponse<RankData>?> {
        return safeApiCall {
            requestService.getTotalRank()
        }
    }

    suspend fun getCompulsoryRank(): ResultWrapper<ApiResponse<RankData>?> {
        return safeApiCall {
            requestService.getCompulsoryRank()
        }
    }

    suspend fun getCalendar(): ResultWrapper<ApiResponse<CalendarData>?> {
        return safeApiCall {
            requestService.getCalendar()
        }
    }

    suspend fun getTodayClassroom(): ResultWrapper<ApiResponse<ClassroomData>?> {
        return safeApiCall {
            requestService.getTodayClassroom()
        }
    }

    suspend fun getTomorrowClassroom(): ResultWrapper<ApiResponse<ClassroomData>?> {
        return safeApiCall {
            requestService.getTomorrowClassroom()
        }
    }

}