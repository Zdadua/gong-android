package com.sky31.gonggong.service

import android.util.Log
import com.sky31.gonggong.dao.PublicDao
import com.sky31.gonggong.entity.ClassroomData
import com.sky31.gonggong.entity.database.PublicEntity

/**
 * 空教室数据的请求、存储和获取
 *
 * @param service 空教室API服务
 * @param dao Room数据库
 */
class DealClassroomService(service: ClassroomService, dao: PublicDao) : DealRequestService() {
    private var apiService = service
    private val publicDao = dao

    /**
     * 获取今日空教室表并存入Room
     */
    suspend fun getTodayClassroom(): RequestResult {
        return getAndStore(
            apiCall = { apiService.getTodayClassroom() },
            storage = { data ->
                data?.data?.let {
                    if (publicDao.getPublicData() == null) {
                        publicDao.insertPublicData(
                            PublicEntity(
                                id = 1,
                                todayClassroom = it,
                                tomorrowClassroom = null,
                                calendar = null
                            )
                        )
                    } else {
                        publicDao.updateTodayClassroom(it)
                    }
                }
            }
        )
    }

    /**
     * 获取明日空教室表并存入Room
     */
    suspend fun getTomorrowClassroom(): RequestResult {
        return getAndStore(
            apiCall = { apiService.getTomorrowClassroom() },
            storage = { data ->
                data?.data?.let {
                    if (publicDao.getPublicData() == null) {
                        publicDao.insertPublicData(
                            PublicEntity(
                                id = 1,
                                todayClassroom = null,
                                tomorrowClassroom = it,
                                calendar = null
                            )
                        )
                    } else {
                        publicDao.updateTomorrowClassroom(it)
                    }
                }
            }
        )
    }

    fun setService(service: ClassroomService) {
        apiService = service
        Log.i(TAG, "reset classroomService")
    }

    /**
     * 从Room中获取今日空教室表
     */
    suspend fun getTodayClassroomFromDatabase(): ClassroomData? = publicDao.getTodayClassroom()

    /**
     * 从Room中获取明日空教室表
     */
    suspend fun getTomorrowClassroomFromDatabase(): ClassroomData? =
        publicDao.getTomorrowClassroom()
}