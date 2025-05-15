package com.sky31.gonggong.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * 主题色
 */
val Orange01 = Color(0xFFF8931D)

/**
 * 系统主题色
 */
interface ThemeColor {
    val backgroundColor: Color
    val textPrimary: Color
    val textSecondary: Color
    val boxColorPrimary: Color
    val boxColorSecondary: Color
    val drawerColor: Color
}

object DarkColor: ThemeColor {
    override val backgroundColor: Color = Color(0xFF1C1C1C)
    override val textPrimary: Color = Color.White
    override val textSecondary: Color = Color(0xFFB9B9B9)
    override val boxColorPrimary: Color = Color(0xFF313131)
    override val boxColorSecondary: Color = Color(0xFF1C1C1C)
    override val drawerColor: Color = Color(0xFF494949)
}

object LightColor: ThemeColor {
    override val backgroundColor: Color = Color(0xFFFFFFFF)
    override val textPrimary: Color = Color.Black
    override val textSecondary: Color = Color(0xFF515151)
    override val boxColorPrimary: Color = Color(0xFFECECEC)
    override val boxColorSecondary: Color = Color(0xFFFFFFFF)
    override val drawerColor: Color = Color(0xFFE5F3E3)
}

val LocalThemeColor = compositionLocalOf<ThemeColor> { DarkColor }