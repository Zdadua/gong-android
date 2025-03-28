package com.sky31.gonggong.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sky31.gonggong.dao.UserDao
import com.sky31.gonggong.entity.UserEntity
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val mutex = Mutex()

        suspend fun getInstance(context: Context): AppDatabase {
            INSTANCE?.let { return it }

            mutex.withLock {
                if(INSTANCE !== null) return INSTANCE as AppDatabase

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gong_db"
                ).build()

                INSTANCE = instance

                return instance
            }
        }
    }
}