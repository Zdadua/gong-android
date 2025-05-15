package com.sky31.gonggong.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sky31.gonggong.dao.AcademicDao
import com.sky31.gonggong.dao.CourseDao
import com.sky31.gonggong.dao.ExamDao
import com.sky31.gonggong.dao.PublicDao
import com.sky31.gonggong.dao.UserDao
import com.sky31.gonggong.database.typeconverter.Converters
import com.sky31.gonggong.entity.database.AcademicEntity
import com.sky31.gonggong.entity.database.CourseEntity
import com.sky31.gonggong.entity.database.ExamEntity
import com.sky31.gonggong.entity.database.PublicEntity
import com.sky31.gonggong.entity.database.UserEntity

@Database(
    entities = [UserEntity::class, PublicEntity::class, ExamEntity::class, CourseEntity::class, AcademicEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getAcademicDao(): AcademicDao
    abstract fun getPublicDao(): PublicDao
    abstract fun getCourseDao(): CourseDao
    abstract fun getExamDao(): ExamDao

    companion object {
        const val NAME = "gong_db"
    }
}