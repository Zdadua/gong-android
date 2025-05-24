package com.sky31.gonggong.service

import android.util.Log
import com.sky31.gonggong.dao.AcademicDao
import com.sky31.gonggong.dao.CourseDao
import com.sky31.gonggong.dao.ExamDao
import com.sky31.gonggong.dao.PublicDao
import com.sky31.gonggong.dao.UserDao
import com.sky31.gonggong.database.AppDatabase
import com.sky31.gonggong.module.RetrofitFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * 集中管理Room Dao
 */
class AppRepository @Inject constructor(
    database: AppDatabase
) {
    private val TAG = "AppRepository"
    private var retrofit: Retrofit = RetrofitFactory.create()
    private val userDao: UserDao = database.getUserDao()
    private val examDao: ExamDao = database.getExamDao()
    private val academicDao: AcademicDao = database.getAcademicDao()
    private val publicDao: PublicDao = database.getPublicDao()
    private val courseDao: CourseDao = database.getCourseDao()

    private var dealLoginService = DealLoginService(
        retrofit.create(LoginService::class.java),
        userDao
    )
    private var dealExamService = DealExamService(
        retrofit.create(ExamService::class.java),
        examDao
    )
    private var dealAcademicService = DealAcademicService(
        retrofit.create(AcademicService::class.java),
        academicDao
    )

    private var dealCourseService = DealCourseService(
        retrofit.create(CourseService::class.java),
        courseDao
    )

    private var dealClassroomService = DealClassroomService(
        retrofit.create(ClassroomService::class.java),
        publicDao
    )

    /**
     * 清空Room中的user数据
     */
    suspend fun clearAll() {
        userDao.clearAll()
        examDao.clearAll()
        academicDao.clearAll()
        publicDao.clearAll()
        courseDao.clearAll()
    }

    fun setAuthorization(token: String) {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originRequest = chain.request()
                val newRequest = originRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                chain.proceed(newRequest)
            }.build()

        retrofit = RetrofitFactory.createWithClient(client)
        resetDealService()
        Log.i(TAG, "setAuthorization: $token")
    }

    fun clearAuthorization() {
        retrofit = RetrofitFactory.create()
        resetDealService()
    }

    private fun resetDealService() {
        dealLoginService.setService(retrofit.create(LoginService::class.java))
        dealExamService.setService(retrofit.create(ExamService::class.java))
        dealAcademicService.setService(retrofit.create(AcademicService::class.java))
        dealCourseService.setService(retrofit.create(CourseService::class.java))
        dealClassroomService.setService(retrofit.create(ClassroomService::class.java))
    }

    fun getDealLoginService(): DealLoginService = dealLoginService
    fun getDealExamService(): DealExamService = dealExamService
    fun getDealAcademicService(): DealAcademicService = dealAcademicService
    fun getDealCourseService(): DealCourseService = dealCourseService
    fun getDealClassroomService(): DealClassroomService = dealClassroomService
}