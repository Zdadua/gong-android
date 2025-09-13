package com.sky31.gonggong.manager

import com.sky31.gonggong.dao.ConfigDao
import com.sky31.gonggong.entity.GlobalConfig
import com.sky31.gonggong.entity.database.ConfigEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppConfigManager(
    private val configDao: ConfigDao
) {
    private val _currentGlobalConfig = MutableStateFlow(GlobalConfig())
    val currentGlobalConfig = _currentGlobalConfig.asStateFlow()

    private val coroutine = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        coroutine.launch {
            loadConfig()
        }
    }

    /**
     * 加载本地配置
     */
    private suspend fun loadConfig() {
        val config = configDao.getConfig() ?: ConfigEntity()

        config.globalConfig?.let { _currentGlobalConfig.value = it }
    }

    /**
     * 更新配置
     *
     * @param update 配置更新函数
     */
    suspend fun updateConfig(update: (ConfigEntity) -> ConfigEntity) {
        val newConfig = update(ConfigEntity(globalConfig = _currentGlobalConfig.value))

        configDao.saveConfigEntity(newConfig)
        loadConfig()
    }
}