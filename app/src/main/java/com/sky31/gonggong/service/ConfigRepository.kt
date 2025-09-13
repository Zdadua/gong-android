package com.sky31.gonggong.service

import com.sky31.gonggong.database.AppDatabase
import com.sky31.gonggong.entity.database.ConfigEntity
import javax.inject.Inject

class ConfigRepository @Inject constructor(
    database: AppDatabase
) {
    private val TAG = "ConfigRepository"
    private val configDao = database.getConfigDao()

    suspend fun getConfig(): ConfigEntity? {
        return configDao.getConfig()
    }
}