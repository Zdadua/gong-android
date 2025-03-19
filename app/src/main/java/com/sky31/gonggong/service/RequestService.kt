package com.sky31.gonggong.service

import com.sky31.gonggong.entity.ApiLoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.Headers
import retrofit2.http.POST

interface RequestService {
    @POST
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun login(@Field("username") username: String, @Field("password") password: String): Call<ApiLoginResponse>
}