package com.sky31.gonggong.database.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sky31.gonggong.entity.InfoData

class Converters {
    @TypeConverter
    fun fromInfo(value: InfoData?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toInfo(value: String?): InfoData? {
        return Gson().fromJson(value, InfoData::class.java)
    }
}