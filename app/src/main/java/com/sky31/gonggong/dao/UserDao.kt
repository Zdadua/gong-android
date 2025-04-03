package com.sky31.gonggong.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.ClassroomData
import com.sky31.gonggong.entity.InfoData
import com.sky31.gonggong.entity.database.PublicEntity
import com.sky31.gonggong.entity.database.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE user SET info = :info WHERE uid = :uid")
    suspend fun updateInfo(uid: String, info: InfoData): Int

    @Query("SELECT * FROM user WHERE uid = :uid")
    suspend fun getUserByUid(uid: String): UserEntity?

    @Query("INSERT INTO public_data(today_classroom, tomorrow_classroom, calendar) VALUES(:today, :tomorrow, :calendar)")
    suspend fun insertPublicData(today: ClassroomData, tomorrow: ClassroomData, calendar: CalendarData)

    @Query("SELECT * FROM public_data")
    suspend fun getPublicData(): PublicEntity?
}