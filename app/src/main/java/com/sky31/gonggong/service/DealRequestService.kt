package com.sky31.gonggong.service

import retrofit2.Response

open class DealRequestService() {
    val TAG = "RequestService"

    sealed class RequestResult {
        data class Success(val message: String, val code: Int = 0): RequestResult()
        data class Error(val message: String, val code: Int = 0): RequestResult()
    }

    suspend fun <T> getAndStore(apiCall: suspend() -> Response<T>, storage: suspend(data: T?) -> Unit): RequestResult {
        val result = safeApiCall {
            apiCall()
        }

        when(result) {
            is ResultWrapper.Success -> {
                storage(result.data)
                return RequestResult.Success(message = "请求成功", code = result.code)
            }
            is ResultWrapper.Error -> {
                // TODO: 处理错误
                println(result.toString())
                return RequestResult.Error(message = result.message, code = result.code)
            }
            is ResultWrapper.NetworkError -> {
                // TODO: 处理网络错误
                println(result.toString())
                return RequestResult.Error(result.message)
            }
        }
    }
}