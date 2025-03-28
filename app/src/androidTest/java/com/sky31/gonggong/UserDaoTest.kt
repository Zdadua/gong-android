package com.sky31.gonggong

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sky31.gonggong.dao.UserDao
import com.sky31.gonggong.database.AppDatabase
import com.sky31.gonggong.entity.InfoData
import com.sky31.gonggong.entity.UserEntity
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

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

        userDao = db.userDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertUserAndGetById() = runTest {
        val userInfo = InfoData(
            "2021",
            "zyh",
            "male",
            "2000-1-1",
            "jw",
            "2",
            "2019-6-6",
            "xtu"
        )
        val user = UserEntity("2021", "testToken", userInfo)

        userDao.insert(user)
        val retrieved = userDao.getById("2021")
        val retrievedToken = userDao.getTokenById("2021")

        assertEquals(user, retrieved)
        assertEquals(retrievedToken, "testToken")
    }
}