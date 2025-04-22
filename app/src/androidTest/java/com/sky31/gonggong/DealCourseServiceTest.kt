package com.sky31.gonggong

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sky31.gonggong.database.AppDatabase
import com.sky31.gonggong.service.CourseService
import com.sky31.gonggong.service.DealCourseService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class DealCourseServiceTest {
    private lateinit var db: AppDatabase
    private lateinit var mockWebServer: MockWebServer
    private lateinit var dealCourseService: DealCourseService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val courseService = retrofit.create(CourseService::class.java)

        runBlocking {
            db = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase::class.java
            ).allowMainThreadQueries()
                .build()
        }

        val courseDao = db.getCourseDao()
        dealCourseService = DealCourseService(courseService, courseDao)
    }

    @After
    fun shutdown() {
         mockWebServer.shutdown()
    }

    @Test
    fun testGetCourse() = runTest {
        val body = """
            {
              "code": 0,
              "message": "OK",
              "data": {
                "courses": [
                  {
                    "name": "计算机组成原理",
                    "teacher": "李老师",
                    "classroom": "计算中心102",
                    "weeks": "4-5,8,10-21",
                    "start_time": 1,
                    "duration": 2,
                    "day": "星期六"
                  }
                ]
              }
            }
        """.trimIndent()

        val mockResponse = MockResponse()
            .setBody(body)
            .setResponseCode(200)
            .addHeader("Content-Type", "application/json")

        mockWebServer.enqueue(mockResponse)

        val result = dealCourseService.getCourse()

        assert(result)

        val courses = dealCourseService.getCourseFromDatabase()

        println(courses)
    }

}