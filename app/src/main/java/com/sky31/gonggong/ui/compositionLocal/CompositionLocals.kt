package com.sky31.gonggong.ui.compositionLocal

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import com.sky31.gonggong.entity.GlobalConfig

val LocalGlobalConfig = staticCompositionLocalOf<GlobalConfig> {
    error("No GlobalConfig provided! Please wrap your app with GlobalConfigProvider.")
}

val LocalIsDarkTheme = staticCompositionLocalOf<Boolean> {
    error("No IsDarkTheme provided! Please wrap your app with IsDarkThemeProvider.")
}

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}