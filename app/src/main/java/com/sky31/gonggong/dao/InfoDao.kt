package com.sky31.gonggong.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sky31.gonggong.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<UserEntity>

    @Query("SELECT * FROM user WHERE uid = :id")
    suspend fun getById(id: String): UserEntity

    @Query("SELECT token FROM user WHERE uid = :id")
    suspend fun getTokenById(id: String): String

    @Insert
    suspend fun insert(user: UserEntity)

    @Query("DELETE FROM user WHERE uid = :id")
    suspend fun deleteById(id: String)
}