package com.sky31.gonggong.service

import com.sky31.gonggong.MainApplication
import com.sky31.gonggong.dao.UserDao
import com.sky31.gonggong.entity.ApiLoginResponse
import com.sky31.gonggong.entity.database.UserEntity
import okhttp3.OkHttpClient

class DealLoginService(service: LoginService, dao: UserDao) {
    private val apiService = service
    private val userDao = dao

    suspend fun login(username: String, password: String): ResultWrapper<ApiLoginResponse?> {
        val result = safeApiCall {
            apiService.login(username, password)
        }

        when(result) {
            is ResultWrapper.Success -> {
                userDao.insertUser(UserEntity(username, result.data?.accessToken, null))

                val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val originRequest = chain.request()
                        val newRequest = originRequest.newBuilder()
                            .header("Authorization", "Bearer " + result.data?.accessToken)
                            .build()

                        chain.proceed(newRequest)
                    }.build()

                MainApplication.retrofit = MainApplication.retrofit.newBuilder()
                    .client(client)
                    .build()

            }
            is ResultWrapper.Error -> {
                println(result.toString())
            }
            is ResultWrapper.NetworkError -> {
                println(result.toString())
            }
        }

        return result
    }

    suspend fun logout(): Boolean {
        val result = userDao.deleteUser() > 0
        if(!result) return false

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originRequest = chain.request()
                val newRequest = originRequest.newBuilder()
                    .removeHeader("Authorization")
                    .build()

                chain.proceed(newRequest)
            }.build()

        MainApplication.retrofit = MainApplication.retrofit.newBuilder()
            .client(client)
            .build()

        return true
    }
}