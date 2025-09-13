package com.sky31.gonggong.ui.theme

enum class ThemeMode {
    SYSTEM,
    DARK,
    LIGHT;

    companion object {
        fun fromString(value: String): ThemeMode? {
            return entries.find { it.name.equals(value, true) }
        }

        fun toString(value: ThemeMode): String {
            return value.name
        }
    }
}