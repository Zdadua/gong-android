package com.sky31.gonggong.service

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T, val code: Int, val message: String) : ResultWrapper<T>()
    data class Error(val code: Int, val message: String) : ResultWrapper<Nothing>()
    data object NetworkError : ResultWrapper<Nothing>()
}

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ResultWrapper<T?> {
    return try {
        val response: Response<T> = apiCall()
        val status = response.code()

        if(response.isSuccessful) {
            ResultWrapper.Success(response.body(), status, response.message())
        } else {
            ResultWrapper.Error(status, response.errorBody()?.string() ?: "Unknown error")
        }
    } catch (e: HttpException) {
        ResultWrapper.Error(e.code(), e.message() ?: "Unknown error")
    } catch (e: IOException) {
        ResultWrapper.NetworkError
    } catch (e: Exception) {
        ResultWrapper.Error(500, e.message ?: "Unknown error")
    }
}