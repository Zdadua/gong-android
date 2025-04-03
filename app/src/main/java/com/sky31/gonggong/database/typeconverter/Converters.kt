package com.sky31.gonggong.database.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.ClassroomData
import com.sky31.gonggong.entity.CourseData
import com.sky31.gonggong.entity.ExamData
import com.sky31.gonggong.entity.InfoData
import com.sky31.gonggong.entity.RankData
import com.sky31.gonggong.entity.ScoreData

class Converters {
    @TypeConverter
    fun fromInfo(value: InfoData?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toInfo(value: String?): InfoData? {
        return Gson().fromJson(value, InfoData::class.java)
    }

    @TypeConverter
    fun fromRankData(value: RankData?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toRankData(value: String?): RankData? {
        return Gson().fromJson(value, RankData::class.java)
    }

    @TypeConverter
    fun fromScoreData(value: ScoreData?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toScoreData(value: String?): ScoreData? {
        return Gson().fromJson(value, ScoreData::class.java)
    }

    @TypeConverter
    fun fromCourseList(value: List<CourseData.CourseElem>): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toCourseList(value: String?): List<CourseData.CourseElem>? {
        val type = object : TypeToken<List<CourseData.CourseElem>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromExamList(value: List<ExamData.ExamElem>): String? {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun toExamList(value: String?): List<ExamData.ExamElem>? {
        val type = object : TypeToken<List<ExamData.ExamElem>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromCalendarData(value: CalendarData?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toCalendarData(value: String?): CalendarData? {
        return Gson().fromJson(value, CalendarData::class.java)
    }

    @TypeConverter
    fun fromClassroomData(value: ClassroomData?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toClassroomData(value: String?): ClassroomData? {
        return Gson().fromJson(value, ClassroomData::class.java)
    }
}