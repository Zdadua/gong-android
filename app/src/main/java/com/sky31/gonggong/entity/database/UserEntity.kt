package com.sky31.gonggong.entity.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sky31.gonggong.entity.InfoData

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val uid: String,
    val token: String?,
    val info: InfoData?
)
