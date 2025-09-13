package com.sky31.gonggong.viewmodel

import androidx.lifecycle.ViewModel
import com.sky31.gonggong.manager.AppConfigManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val configManager: AppConfigManager
) : ViewModel() {
    fun getConfigManager(): AppConfigManager {
        return configManager
    }
}