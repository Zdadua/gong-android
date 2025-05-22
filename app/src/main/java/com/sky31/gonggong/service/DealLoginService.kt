package com.sky31.gonggong.service

import com.sky31.gonggong.dao.UserDao
import com.sky31.gonggong.entity.ApiLoginResponse
import com.sky31.gonggong.entity.database.UserEntity

class DealLoginService(service: LoginService, dao: UserDao) {
    private var apiService = service
    private val userDao = dao

    suspend fun login(username: String, password: String): ResultWrapper<ApiLoginResponse?> {
        val result = safeApiCall {
            apiService.login(username, password)
        }

        when(result) {
            is ResultWrapper.Success -> {
                userDao.insertUser(UserEntity(username, result.data?.accessToken, null))
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
        return userDao.deleteUser() > 0
    }

    fun setService(service: LoginService) {
        apiService = service
    }

    suspend fun getUser() = userDao.getUser()
}