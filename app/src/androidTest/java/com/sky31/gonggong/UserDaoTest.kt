package com.sky31.gonggong

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sky31.gonggong.dao.UserDao
import com.sky31.gonggong.database.AppDatabase
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.ClassroomData
import com.sky31.gonggong.entity.InfoData
import com.sky31.gonggong.entity.database.UserEntity
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        runBlocking {
            db = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase::class.java
            ).allowMainThreadQueries()
                .build()
        }

        userDao = db.getUserDao()

        runBlocking {
            val testInfo = InfoData("1","1","1","1","1","1","1","1")

            val userEntity = UserEntity("test", "test_token", testInfo)

            userDao.insertUser(userEntity)
        }
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testGetUser() = runTest {
        val retrieved = userDao.getUserByUid("test")

        assert(retrieved?.uid == "test")
    }

    @Test
    fun testUpdateInfo() = runTest {
        val info = InfoData("2021","1","456","1","1","1","1","1")
        val rowUpdated = userDao.updateInfo("test", info)
        println(rowUpdated)

        val retrieved = userDao.getUserByUid("test")
        assert(retrieved?.info?.studentId == "2021")
    }

    @Test
    fun testInsertUser() = runTest {
        userDao.insertUser(UserEntity("test02", "test_token", null))
        val retrieved = userDao.getUserByUid("test02")

        assert(retrieved?.uid == "test02")
    }

    @Test
    fun testInsertPublicData() = runTest {
        val today = ClassroomData(
            "2000-01-01",
            mapOf("逸夫楼" to listOf(ClassroomData.ClassroomInfo("程序设计", listOf("空","空","空","空","空"))))
        )

        userDao.insertPublicData(today, today, CalendarData("2000-02-02", 24, "1"))
        val retrieved = userDao.getPublicData()
        assert(retrieved?.calendar?.weeks == 24)
    }
}