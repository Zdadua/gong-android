package com.sky31.gonggong.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sky31.gonggong.service.AppRepository
import com.sky31.gonggong.service.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * 认证ViewModel
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AppRepository
) : ViewModel() {
    private val dealLoginService get() = repo.getDealLoginService()

    // 用户登录状态
    private val _authState = mutableStateOf<AuthState>(AuthState.Unauthenticated)
    val authState = _authState

    // 初始化时，从数据库中获取用户信息，更新认证状态
    init {
        runBlocking {
            updateAuthStateFromDB()
        }
    }

    /**
     * 从数据库中获取用户信息，更新认证状态
     */
    private suspend fun updateAuthStateFromDB() {
        val user = dealLoginService.getUser()

        Log.i("AuthViewModel", user.toString())
        if (user != null) {
            user.token?.let {
                _authState.value = AuthState.Authenticated(it)
                repo.setAuthorization(it)
            }
        } else {
            _authState.value = AuthState.Unauthenticated
            repo.clearAuthorization()
        }
    }

    /**
     * 重置认证状态，Error -> Unauthenticated
     */
    fun resetAuthState() {
        _authState.value = AuthState.Unauthenticated
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     */
    suspend fun login(username: String, password: String) {
        _authState.value = AuthState.Loading

        when(val result = dealLoginService.login(username, password)) {
            is ResultWrapper.Success -> {
                updateAuthStateFromDB()
            }

            is ResultWrapper.Error -> {
                _authState.value = AuthState.Error(result.message)
            }

            is ResultWrapper.NetworkError -> {
                Log.i("login", "NetworkError: ${result.message}")
                _authState.value = AuthState.Error(result.toString())
            }
        }
    }

    /**
     * 用户登出
     */
    suspend fun logout() {
        dealLoginService.logout()
        updateAuthStateFromDB()
        repo.clearAll()
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