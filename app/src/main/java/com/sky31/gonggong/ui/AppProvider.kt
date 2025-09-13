package com.sky31.gonggong.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sky31.gonggong.ui.compositionLocal.LocalGlobalConfig
import com.sky31.gonggong.ui.compositionLocal.LocalIsDarkTheme
import com.sky31.gonggong.ui.compositionLocal.LocalNavController
import com.sky31.gonggong.ui.theme.ThemeMode
import com.sky31.gonggong.ui.theme.darkColorScheme
import com.sky31.gonggong.ui.theme.lightColorScheme
import com.sky31.gonggong.ui.theme.typography
import com.sky31.gonggong.viewmodel.GlobalViewModel

@Composable
fun AppProvider(
    navController: NavController,
    content: @Composable () -> Unit
) {
    val globalViewModel: GlobalViewModel = hiltViewModel()

    val configManager = globalViewModel.getConfigManager()
    val config by configManager.currentGlobalConfig.collectAsState()

    val isSystemInDarkTheme = isSystemInDarkTheme()

    val isDarkTheme = remember {
        derivedStateOf {
            when (config.themeMode) {
                ThemeMode.DARK -> true
                ThemeMode.LIGHT -> false
                else -> isSystemInDarkTheme
            }
        }
    }
    val colorScheme = remember {
        derivedStateOf {
            if (isDarkTheme.value) darkColorScheme else lightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme.value,
        typography = typography
    ) {
        CompositionLocalProvider(
            LocalGlobalConfig provides config,
            LocalIsDarkTheme provides isDarkTheme.value,
            LocalNavController provides navController
        ) {
            content()
        }
    }
}