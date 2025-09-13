package com.sky31.gonggong.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sky31.gonggong.entity.GlobalConfig

@Entity(tableName = "config_data")
data class ConfigEntity(
    @PrimaryKey val uid: Int? = 1,
    @ColumnInfo(name = "global_config") val globalConfig: GlobalConfig? = GlobalConfig()
)
