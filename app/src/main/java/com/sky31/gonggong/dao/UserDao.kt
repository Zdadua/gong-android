package com.sky31.gonggong.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.ClassroomData
import com.sky31.gonggong.entity.InfoData
import com.sky31.gonggong.entity.database.PublicEntity
import com.sky31.gonggong.entity.database.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Query("UPDATE user SET info = :info WHERE uid = :uid")
    suspend fun updateInfo(uid: String, info: InfoData): Int

    @Query("SELECT * FROM user")
    suspend fun getUser(): UserEntity?

    @Query("SELECT * FROM user WHERE uid = :uid")
    suspend fun getUserByUid(uid: String): UserEntity?

    @Query("SELECT COUNT(*) FROM user")
    suspend fun getUserNum(): Int

    @Query("DELETE FROM user")
    suspend fun deleteUser(): Int
}