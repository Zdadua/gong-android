package com.sky31.gonggong.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sky31.gonggong.entity.InfoData
import com.sky31.gonggong.entity.database.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("INSERT INTO user(uid, token) VALUES (:uid, :token)")
    suspend fun insertUserWithoutInfo(uid: String, token: String)

    @Query("UPDATE user SET info = :info WHERE uid = :uid")
    suspend fun updateInfo(uid: String, info: InfoData): Int

    @Query("SELECT * FROM user WHERE uid = :uid")
    suspend fun getUserByUid(uid: String): UserEntity?
}