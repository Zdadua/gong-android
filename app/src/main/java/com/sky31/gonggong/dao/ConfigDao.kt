package com.sky31.gonggong.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sky31.gonggong.entity.database.ConfigEntity

@Dao
interface ConfigDao {
    @Query("SELECT * FROM config_data")
    suspend fun getConfig(): ConfigEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveConfigEntity(data: ConfigEntity)
}