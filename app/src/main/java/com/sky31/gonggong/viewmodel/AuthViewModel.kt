package com.sky31.gonggong.viewmodel

import androidx.lifecycle.ViewModel
import com.sky31.gonggong.MainApplication
import com.sky31.gonggong.entity.database.UserEntity
import com.sky31.gonggong.service.DealLoginService
import com.sky31.gonggong.service.LoginService
import com.sky31.gonggong.service.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class AuthViewModel: ViewModel() {

    private val userDao by lazy { MainApplication.appDatabase.getUserDao() }
    private val dealLoginService by lazy {
        val service = MainApplication.retrofit.create(LoginService::class.java)
        DealLoginService(service, userDao)
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        runBlocking {
            checkAuth()
        }
    }

    /**
     * 从数据库中获取用户信息，更新认证状态
     */
    private suspend fun updateAuthStateFromDB() {
        val user = userDao.getUser()
        if (user != null) {
            user.token?.let { _authState.value = AuthState.Authenticated(it) }
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }

    fun resetAuthState() {
        _authState.value = AuthState.Unauthenticated
    }

    /**
     * 检查用户是否已经登录
     */
    private suspend fun checkAuth() {
        val user = userDao.getUser()
        if (user != null) {
            user.token?.let { _authState.value = AuthState.Authenticated(it) }
        }
    }

    suspend fun login(username: String, password: String) {
        _authState.value = AuthState.Loading

        when(val result = dealLoginService.login(username, password)) {
            is ResultWrapper.Success -> {
                result.data?.let { userDao.insertUser(UserEntity(username, it.accessToken, null)) }
                updateAuthStateFromDB()
            }

            is ResultWrapper.Error -> {
                _authState.value = AuthState.Error(result.message)
            }

            is ResultWrapper.NetworkError -> {
                _authState.value = AuthState.Error(result.toString())
            }
        }
    }

    suspend fun logout() {
        dealLoginService.logout()
        updateAuthStateFromDB()
    }

}

/**
 * 认证状态密封类
 */
sealed class AuthState {
    data object Unauthenticated: AuthState()
    data object Loading: AuthState()
    data class Authenticated(val token: String): AuthState()

    data class Error(val message: String): AuthState()
}