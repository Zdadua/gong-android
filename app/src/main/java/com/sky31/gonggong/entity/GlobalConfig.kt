package com.sky31.gonggong.entity

import com.google.gson.annotations.SerializedName
import com.sky31.gonggong.ui.theme.ThemeMode

data class GlobalConfig(
    @SerializedName("theme_mode")
    val themeMode: ThemeMode? = ThemeMode.SYSTEM
)