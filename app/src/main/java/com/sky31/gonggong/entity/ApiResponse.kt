package com.sky31.gonggong.entity

data class ApiResponse<T>(
    val code: Int,
    val message: String,
    val data: T
)
