package com.sky31.gonggong.database

import androidx.room.Database
import com.sky31.gonggong.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TestDatabase: AppDatabase()